package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.reaper.ancientnature.common.entity.goals.*;
import net.reaper.ancientnature.common.entity.ground.util.IHasCustomizableAttributes;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.TameableOrders;
import net.reaper.ancientnature.common.entity.util.IMouseInput;
import net.reaper.ancientnature.common.entity.util.IRoaringMob;
import net.reaper.ancientnature.common.event.CameraShakeManager;
import net.reaper.ancientnature.common.pathfinding.raycoms.AdvancedPathNavigate;
import net.reaper.ancientnature.common.pathfinding.raycoms.PathingStuckHandler;
import net.reaper.ancientnature.common.util.ANMath;
import net.reaper.ancientnature.common.util.EntityUtils;
import net.reaper.ancientnature.core.init.ModEffects;
import net.reaper.ancientnature.core.init.ModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static net.reaper.ancientnature.common.pathfinding.raycoms.PathingStuckHandler.createStuckHandler;

public abstract class AbstractTrexEntity extends TamableAnimal implements IHasCustomizableAttributes, Saddleable, IMouseInput, IRoaringMob {

    private static final EntityDataAccessor<Boolean> GENDER = SynchedEntityData.defineId(AbstractTrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(AbstractTrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(AbstractTrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(AbstractTrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.defineId(AbstractTrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ORDER = SynchedEntityData.defineId(AbstractTrexEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(AbstractTrexEntity.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SPRINTING = SynchedEntityData.defineId(AbstractTrexEntity.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> STAMINA = SynchedEntityData.defineId(AbstractTrexEntity.class,EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HUNGER = SynchedEntityData.defineId(AbstractTrexEntity.class,EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> INTIMATED = SynchedEntityData.defineId(AbstractTrexEntity.class,EntityDataSerializers.BOOLEAN);
    public int tickControlled;
    public int poseTicks;
    public SmartAnimalPose currentPose = SmartAnimalPose.IDLE;
    protected AbstractTrexEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.configureAttributes();
        this.setMaxUpStep(1.3F);
        this.setStamina(100);
        this.setHunger(100);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 75D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 5f);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return ModEntities.TREX.get().create(serverLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RoarGoal(this, 50, 100));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(6, new AIEscort(this, 1.0D));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.4D));
        this.goalSelector.addGoal(8, new DinoRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new CooldownAfterKillGoal(this, 200));

        this.goalSelector.addGoal(10, new AILookIdle(this));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.2D, Ingredient.of(Items.COOKED_BEEF), true));

        this.targetSelector.addGoal(0, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new AINonTamed<>(this, LivingEntity.class, false, entity -> (!(entity instanceof Player) || !((Player) entity).isCreative())));
        this.targetSelector.addGoal(5, new AITargetGoal<>(this, LivingEntity.class, true, entity -> entity instanceof LivingEntity && entity.getType() != AbstractTrexEntity.this.getType()));
        this.targetSelector.addGoal(5, new AITargetGoal<>(this, Player.class, true, entity -> entity instanceof LivingEntity && entity.getType() != AbstractTrexEntity.this.getType()));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GENDER, false);
        this.entityData.define(SLEEPING, false);
        this.entityData.define(ATTACKING, false);
        this.entityData.define(EATING, false);
        this.entityData.define(ROARING, false);
        this.entityData.define(ORDER, TameableOrders.IDLE.ordinal());
        this.entityData.define(SADDLED, false);
        this.entityData.define(SPRINTING, false);
        this.entityData.define(STAMINA, 0.0F);
        this.entityData.define(HUNGER, 0.0F);
        this.entityData.define(INTIMATED, false);
    }

    @Override
    public void configureAttributes() {
    }
    protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type) {
        return createNavigator(worldIn, type, createStuckHandler());
    }

    protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type, PathingStuckHandler stuckHandler) {
        return createNavigator(worldIn, type, stuckHandler, 4f, 4f);
    }
    protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type, PathingStuckHandler stuckHandler, float width, float height) {
        AdvancedPathNavigate newNavigator = new AdvancedPathNavigate(this, level(), type, width, height);
        this.navigation = newNavigator;
        newNavigator.setCanFloat(true);
        newNavigator.getNodeEvaluator().setCanOpenDoors(true);
        return newNavigator;
    }

    protected void switchNavigator(int navigatorType) {
        if (navigatorType == 0) {
            this.moveControl = new GroundMoveHelper(this);
            this.navigation = createNavigator(level(), AdvancedPathNavigate.MovementType.WALKING, createStuckHandler().withTeleportSteps(5));
        }
    }

    public boolean getGender() {
        return this.entityData.get(GENDER);
    }

    public void setGender(boolean gender) {
        this.entityData.set(GENDER, gender);
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isEating() {
        return this.entityData.get(EATING);
    }

    public void setEating(boolean eating) {
        this.entityData.set(EATING, eating);
    }

    public boolean isRoaring() {
        return this.entityData.get(ROARING);
    }

    public void setRoaring(boolean roaring) {
        this.entityData.set(ROARING, roaring);
    }

    public boolean isSprinting() {
        return this.entityData.get(SPRINTING);
    }

    public void setSprinting(boolean sprinting) {
        this.entityData.set(SPRINTING, sprinting);
    }

    public float getStamina()
    {
        return this.entityData.get(STAMINA);
    }

    public void setStamina(float stamina)
    {
        this.entityData.set(STAMINA,stamina);
    }

    public float getHunger()
    {
        return this.entityData.get(HUNGER);
    }

    public void setHunger(float hunger)
    {
        this.entityData.set(HUNGER,hunger);
    }

    public TameableOrders getOrder() {
        return TameableOrders.values()[this.entityData.get(ORDER)];
    }

    public void setOrder(TameableOrders order) {
        this.entityData.set(ORDER, order.ordinal());
    }

    public int getPoseTicks() {
        return poseTicks;
    }

    public void setPoseTicks(int poseTicks) {
        this.poseTicks = Math.max(0, poseTicks);
    }

    public SmartAnimalPose getSmartPose() {
        return SmartAnimalPose.values()[currentPose.ordinal()];
    }

    public void setSmartPose(SmartAnimalPose pose) {
        this.currentPose = pose;
    }

    protected abstract int getAnimationLengthInTicks(SmartAnimalPose smartPose);

    @Override
    public boolean canCollideWith(Entity pEntity) {
        return pEntity.isAlive();
    }

    @Override
    public boolean isSaddleable() {
        return this.isTame() && !this.isBaby();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        this.entityData.set(SADDLED, true);
        if (soundSource != null) {
            this.level().playSound(null, this, SoundEvents.HORSE_SADDLE, soundSource, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    public boolean isIntimated()
    {
        return this.entityData.get(INTIMATED);
    }

    public void setIntimated(boolean intimated)
    {
        this.entityData.set(INTIMATED,intimated);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Gender", this.getGender());
        compound.putBoolean("Sleeping", this.isSleeping());
        compound.putBoolean("TamedDragon", this.isTame());
        compound.putInt("order", getOrder().ordinal());
        compound.putBoolean("Saddled", this.isSaddled());
        compound.putFloat("Stamina",this.getStamina());
        compound.putFloat("Hunger",this.getHunger());
        compound.putBoolean("Intimated",this.isIntimated());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setGender(compound.getBoolean("Gender"));
        this.setSleeping(compound.getBoolean("Sleeping"));
        this.setTame(compound.getBoolean("TamedDragon"));
        this.setOrder(TameableOrders.values()[compound.getInt("order")]);
        this.entityData.set(SADDLED, compound.getBoolean("Saddled"));
        this.setStamina(compound.getFloat("Stamina"));
        this.setHunger(compound.getFloat("Hunger"));
        this.setIntimated(compound.getBoolean("Intimated"));
    }

    @Override
    public void tick() {
        int moveTime = 0;
        super.tick();
        updateSleepState();
        updatePoseTicks();
        updateEntityAttributes();
        if(!level().isClientSide){
            if(this.walkAnimation.isMoving())
            {
                moveTime++;

                int finalMoveTime = moveTime;
                level().players().forEach(player -> {
                    if(player instanceof ServerPlayer sp)
                    {
                        if(finalMoveTime > 0)
                        {
                            CameraShakeManager.applyCameraShake(sp, (finalMoveTime),5.0F);
                        }

                    }
                });
            }
        }
        if(this.isVehicle())
        {
            ++this.tickControlled;
        }
        else{
            this.tickControlled = 0;
        }
    }


    private void updateEntityAttributes() {
        if(level().isClientSide) return;

        float curSpeed = this.getSpeed();
        if (this.walkAnimation.isMoving()) {
            if (this.isSprinting()) {
                float newStamina = Mth.clamp(this.getStamina() - (curSpeed * 0.1F), 0, this.getMaxStamina());
                this.setStamina(newStamina);

                if (newStamina > 0) {
                    this.setSpeed(Mth.clamp(curSpeed, 0.1F, curSpeed * (newStamina / this.getMaxStamina())));
                } else {
                    this.setSprinting(false);
                    this.setSpeed(0.1F);
                }

            } else {
                float staminaRecovery = Mth.clamp(this.getStamina() + 0.05F, 0, this.getMaxStamina());
                this.setStamina(staminaRecovery);
                this.setSpeed(curSpeed);
            }

            if (this.isSprinting() && !this.isEating()) {
                float newHunger = Mth.clamp(this.getHunger() - (curSpeed * 0.05F), 0, this.getMaxHunger());
                this.setHunger(newHunger);

                if (newHunger <= 0) {
                    this.setSprinting(false);
                }
            } else if (this.isEating()) {
                float hungerRecovery = Mth.clamp(this.getHunger() + 0.1F, 0, this.getMaxHunger());
                this.setHunger(hungerRecovery);
            }
        }
    }

    private float getMaxHunger()
    {
        return 100.0F;
    }

    private float getMaxStamina()
    {
        return 100.0F;
    }


    private void updateSleepState() {
        if (isSleeping()) {
            setDeltaMovement(0, getDeltaMovement().y, 0);
        }
    }

    private void updatePoseTicks() {
        if (poseTicks > 0) {
            poseTicks--;
        }
    }

    public boolean canMove() {
        return !this.isOrderedToSit() && !this.isSleeping() &&
                this.getControllingPassenger() == null && !this.isPassenger()
                && !(this.getHunger() > 10)&& this.getStamina() != 0 && !this.isIntimated();
    }

    @Override
    public boolean isAggressive() {
        return true;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity pTarget) {
        return !(pTarget instanceof AbstractTrexEntity) && !this.isIntimated();
    }

    @Override
    protected void tickRidden(@NotNull Player pRider, @NotNull Vec3 pTravelVector) {
        super.tickRidden(pRider, pTravelVector);
        this.setTarget(null);
        if (pRider.xxa != 0.0F || pRider.zza != 0.0F) {
            EntityUtils.smoothVehicleYawToRider(pRider, this, 0.1F, 0.14F);
        }
    }

    public @NotNull InteractionResult mobInteract(Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        Item itemForTaming = Items.COOKED_BEEF;


//        if (itemstack.is(itemForTaming) && !isTame()) {
//            if (this.level().isClientSide()) {
//                return InteractionResult.CONSUME;
//            } else {
//                if (!pPlayer.getAbilities().instabuild) {
//                    itemstack.shrink(1);
//                }
//
//
//            }
//        }
        RandomSource source = this.level().getRandom();
        double chance = source.nextDouble();
        if(!(chance < 0.1))
        {
            if(this.isIntimated())
            {
                if (this.isEating()) {
                    if (!this.isTame()) {
                        this.tame(Objects.requireNonNull(level().getNearestPlayer(this, 4)));

                        this.navigation.recomputePath();

                        this.setTarget(null);

                        this.level().broadcastEntityEvent(this, (byte) 7);

                        this.setOrderedToSit(true);
                        this.setInSittingPose(true);

                    }
                }

            }
        }

        if (isTame()) {
            if (pHand == InteractionHand.MAIN_HAND && !isFood(itemstack)) {
                if (itemstack.is(Items.SADDLE) && !this.isSaddled()) {
                    if (!this.level().isClientSide()) {
                        this.entityData.set(SADDLED,true);
                        if (!pPlayer.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }

                else if (isTame() && !isFood(itemstack)) {
                    if (!pPlayer.isCrouching()) {
                        setRiding(pPlayer);
                    } else {

                        setOrder(getOrder() == TameableOrders.SIT ? TameableOrders.FOLLOW : TameableOrders.SIT);
                        boolean currentlySitting = isOrderedToSit();
                        setOrderedToSit(!currentlySitting);
                        setInSittingPose(!currentlySitting);

                        if (isOrderedToSit()) {
                            this.getNavigation().stop();
                        } else {
                            this.getNavigation().recomputePath();
                        }
                    }
                    return InteractionResult.SUCCESS;
                }

            }
        }

        InteractionResult interactionResult = super.mobInteract(pPlayer, pHand);
        if (!interactionResult.consumesAction()) {
            return itemstack.is(Items.SADDLE) ? itemstack.interactLivingEntity(pPlayer, this, pHand) : InteractionResult.PASS;
        }

        return interactionResult;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return ((LivingEntity) this.getFirstPassenger());
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        return new GroundPathNavigation(this,pLevel);
    }

    private void setRiding(Player pPlayer) {
        if(this.isSaddled() && !this.isSitting() || !this.isInSittingPose()){
            pPlayer.setYRot(this.getYRot());
            pPlayer.setXRot(this.getXRot());
            pPlayer.startRiding(this);
        }
    }
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.COOKED_BEEF;
    }

    public boolean isSitting() {
        return getSmartPose()==SmartAnimalPose.SIT;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount)
    {
        if(!level().isClientSide && source.getEntity() != null && this.getRandom().nextInt(4) == 0)
        {
            this.roar();
        }
        return !this.isInvulnerableTo(source) && super.hurt(source, amount);
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if(this.isVehicle() && getControllingPassenger() instanceof Player) {
            LivingEntity livingentity = this.getControllingPassenger();
            this.setYRot(livingentity.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(livingentity.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;
            float f = livingentity.xxa * 0.5F;
            float f1 = livingentity.zza;

            // Inside this if statement, we are on the client!
            if (this.isControlledByLocalInstance()) {
                float newSpeed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
                // increasing speed by 100% if the spring key is held down (number for testing purposes)
                if(Minecraft.getInstance().options.keySprint.isDown()) {
                    newSpeed *= 2f;
                    this.setSprinting(true);
                }
                else this.setSprinting(false);
                this.setSpeed(newSpeed);
                super.travel(new Vec3(f, pTravelVector.y, f1));
            }
        } else {
            this.setSprinting(false);
            super.travel(pTravelVector);
        }
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity pLivingEntity) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() != Direction.Axis.Y) {
            int[][] offsets = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (Pose pose : pLivingEntity.getDismountPoses()) {
                AABB aabb = pLivingEntity.getLocalBoundsForPose(pose);

                for (int[] offset : offsets) {
                    blockpos$mutableblockpos.set(blockpos.getX() + offset[0], blockpos.getY(), blockpos.getZ() + offset[1]);
                    double d0 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutableblockpos, d0);
                        if (DismountHelper.canDismountTo(this.level(), pLivingEntity, aabb.move(vec3))) {
                            pLivingEntity.setPose(pose);
                            return vec3;
                        }
                    }
                }
            }
        }

        return super.getDismountLocationForPassenger(pLivingEntity);
    }


    public void roar() {
        if (this.isDeadOrDying()) {
            return;
        }

        if (!isRoaring()) {
            this.setRoaring(true);
        }

        final int size = 30;
        final List<Entity> entities = level().getEntities(this, this.getBoundingBox().expandTowards(size, size, size));

        for (final Entity entity : entities) {
            final boolean isStrongerDragon = entity instanceof AbstractTrexEntity;
            if (entity instanceof LivingEntity living && !isStrongerDragon) {
                if (this.isOwnedBy(living) ) {
                    living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30 * size));
                } else {
                    living.addEffect(new MobEffectInstance(ModEffects.SCARE.get(),30*size));
                }
                this.stopRoar();
            }
            if (!level().isClientSide) {
                level().players().forEach(player -> {
                    double distance = this.distanceTo(player);
                    if (distance < 40.0) {
                        if(player instanceof ServerPlayer sp)
                        {
                            CameraShakeManager.applyCameraShake(sp,100,5.0F);
                        }
                    }
                });
            }
        }

    }

    @Override
    public void stopRoar() {
        this.setRoaring(false);
    }

    @Override
    public void positionRider(@NotNull Entity passenger, @NotNull MoveFunction callback) {
        super.positionRider(passenger, callback);
        if (this.hasPassenger(passenger)) {

              //  passenger.setPos(passenger.getX(), passenger.getY() + 2, passenger.getZ());

        }
    }


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag)
    {
        this.setStamina(100);
        this.setHunger(100);
        this.setGender(this.random.nextBoolean());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return !this.isLeashed();
    }

    @Override
    public boolean isPushable() {
        return !this.isSleeping();
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
        if (!this.isSleeping()) {
            super.doPush(entity);
        }
    }

    @Override
    protected void pushEntities() {
        if (!this.isSleeping()) {
            super.pushEntities();
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    public boolean isEffectiveAi() {
        return !this.isSleeping() && super.isEffectiveAi();
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source);
    }

    static class GroundMoveHelper extends MoveControl
    {
        private final AbstractTrexEntity mob;
        public GroundMoveHelper(AbstractTrexEntity livingEntityIn) {
            super(livingEntityIn);
            this.mob = livingEntityIn;
        }

        public float distance(float rotateAngleFrom, float rotateAngleTo) {
            return (float) ANMath.atan2_accurate(Mth.sin(rotateAngleTo - rotateAngleFrom), Mth.cos(rotateAngleTo - rotateAngleFrom));
        }

        @Override
        public void tick() {
            if (this.operation == Operation.STRAFE) {
                float f = (float) Objects.requireNonNull(this.mob.getAttribute(Attributes.MOVEMENT_SPEED)).getValue();
                float f1 = (float) this.speedModifier * f;
                float f2 = this.strafeForwards;
                float f3 = this.strafeRight;
                float f4 = Mth.sqrt(f2 * f2 + f3 * f3);

                if (f4 < 1.0F) {
                    f4 = 1.0F;
                }

                f4 = f1 / f4;
                f2 *= f4;
                f3 *= f4;
                float f5 = Mth.sin(this.mob.getYRot() * 0.017453292F);
                float f6 = Mth.cos(this.mob.getYRot() * 0.017453292F);
                float f7 = f2 * f6 - f3 * f5;
                float f8 = f3 * f6 + f2 * f5;
                PathNavigation pathnavigate = this.mob.getNavigation();
                NodeEvaluator nodeprocessor = pathnavigate.getNodeEvaluator();
                if (nodeprocessor.getBlockPathType(this.mob.level(), Mth.floor(this.mob.getX() + (double) f7), Mth.floor(this.mob.getY()), Mth.floor(this.mob.getZ() + (double) f8)) != BlockPathTypes.WALKABLE) {
                    this.strafeForwards = 1.0F;
                    this.strafeRight = 0.0F;
                    f1 = f;
                }
                this.mob.setSpeed(f1);
                this.mob.setZza(this.strafeForwards);
                this.mob.setXxa(this.strafeRight);
                this.operation = Operation.WAIT;
            } else if (this.operation == Operation.MOVE_TO) {
                this.operation = Operation.WAIT;
                double d0 = this.getWantedX() - this.mob.getX();
                double d1 = this.getWantedZ() - this.mob.getZ();
                double d2 = this.getWantedY() - this.mob.getY();
                double d3 = d0 * d0 + d2 * d2 + d1 * d1;

                if (d3 < 2.500000277905201E-7D) {
                    this.mob.setZza(0.0F);
                    return;
                }
                float targetDegree = (float) (Mth.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
                float changeRange = 70F;
                if (Math.ceil(this.mob.getBbWidth()) > 2F) {
                    float ageMod = 1F - Math.min(this.mob.getBbWidth(), this.mob.getBbHeight()) / 125F;
                    changeRange = 5 + ageMod * 10;
                }
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), targetDegree, changeRange));
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (d2 > (double) this.mob.maxUpStep() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth() / 2)) {
                    this.mob.getJumpControl().jump();
                    this.operation = Operation.JUMPING;
                }
            } else if (this.operation == Operation.JUMPING) {
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));

                if (this.mob.onGround()) {
                    this.operation = Operation.WAIT;
                }
            } else {
                this.mob.setZza(0.0F);
            }
        }
    }
}