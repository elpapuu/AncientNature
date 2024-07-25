package net.reaper.ancientnature.common.entity.water;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.reaper.ancientnature.common.config.AncientNatureConfig;
import net.reaper.ancientnature.common.entity.goals.SmallerEntityTargetGoal;
import net.reaper.ancientnature.common.entity.goals.WildBreedGoal;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModEntities;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class Anomalocaris extends AquaticAnimal implements Bucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Anomalocaris.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DIGESTING_ID = SynchedEntityData.defineId(Anomalocaris.class, EntityDataSerializers.BOOLEAN);
    public static final String DIGESTING_TAG = "DigestingTime";
    private final int TICKS_TO_DIGEST = 400; // 20 seconds
    private int ticksEnsnaring;
    private int ticksDigesting;
    private int ticksUntilHungry;
    int HUNGER_COOLDOWN = 9600; // 8 minutes
    boolean isHungry;
    // anim
    boolean isHoldingFood;
    boolean hasEggs;
    private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(Anomalocaris.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState floppingAnimationState = new AnimationState();
    public final AnimationState eatingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;

    public Anomalocaris(EntityType<? extends AquaticAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    public boolean setRiding(Player pPlayer) {
        return false;
    }


    public boolean isMovementBlocked() {
        return false;
    }


    public boolean isRidable() {
        return false;
    }


    public boolean canBeSteered() {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new Anomalocaris.EggBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new Anomalocaris.LayEggGoal(this, 1.0D, 16, 16));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(ItemTags.FISHES), false));
        this.goalSelector.addGoal(5, new Anomalocaris.DashToEnsnareGoal(this));
        this.goalSelector.addGoal(6, new Anomalocaris.MeleeGoal(this, 1.2D, true));
        this.goalSelector.addGoal(7, new Anomalocaris.SwimGoal(this, 1.0D));
        this.goalSelector.addGoal(20, new WildBreedGoal(this, Entity::isInWater, 300));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Anomalocaris.class));
        this.targetSelector.addGoal(2, new SmallerEntityTargetGoal<>(this, WaterAnimal.class, false, p -> p.isInWater() && !p.isPassenger()));
        this.targetSelector.addGoal(2, new SmallerEntityTargetGoal<>(this, AquaticAnimal.class, false, p -> p.isInWater() && !p.isPassenger() && !(p instanceof Anomalocaris)));
    }

    // overridden to apply extra condition for WildBreedGoal
    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.isHungry() && !this.isHoldingFood() && !this.doesHaveEggs();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.MOVEMENT_SPEED, 1.2F)
                .add(Attributes.FOLLOW_RANGE, 4.0D);
    }

    protected float getStandingEyeHeight(@Nonnull Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.9F;
    }

    @Nonnull
    protected PathNavigation createNavigation(@Nonnull Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    @Override
    public void tick() {
        if (this.isAlive() && !this.isNoAi()) {
            if (this.level().isClientSide()) {

                setupAnimationStates();
            } else {
                if (this.getTarget() == null) {
                    if (this.isHoldingFood()) {

                        if (this.hasPassenger(entity -> entity.getBoundingBox().getSize() < this.getBoundingBox().getSize())) this.getFirstPassenger().stopRiding();
                    }
                    this.setAttacking(false);
                } else {

                    this.getLookControl().setLookAt(this.getTarget().getX(), this.getTarget().getEyeY(), this.getTarget().getZ());

                    if (this.isHoldingFood() && getTarget().getMobType() == MobType.WATER) {
                        LivingEntity prey = getTarget();
                        prey.setXRot(this.getXRot());
                        prey.setYRot(this.getYRot());
                        prey.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 5, false, false));

                        if (this.isDigesting()) {

                            --this.ticksDigesting;
                            if (this.ticksDigesting < 0) {

                                this.digest();
                            } else if (this.ticksDigesting % 100 == 0) {

                                if (!this.isSilent()) playMunchSound();
                                prey.hurt(prey.damageSources().mobAttack(this), 0.0F);
                            }
                        } else {
                            ++this.ticksEnsnaring;
                            if (this.ticksEnsnaring >= 10) {

                                this.startDigesting(TICKS_TO_DIGEST);
                            }
                        }
                    } else {

                        this.ticksEnsnaring = -1;
                        this.ticksDigesting = -1;
                        this.setDigesting(false);
                        --this.ticksUntilHungry;
                    }
                }
                if (!this.isHungry() && this.ticksUntilHungry == 0) {

                    setHungry(true);
                }
            }
        }
        super.tick();
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (!this.isInWater()) {
            if (this.idleAnimationState.isStarted()) this.idleAnimationState.stop();
            if (this.eatingAnimationState.isStarted()) this.eatingAnimationState.stop();
            if (this.attackAnimationState.isStarted()) this.attackAnimationState.stop();
            this.floppingAnimationState.startIfStopped(this.tickCount);
        } else {
            this.floppingAnimationState.stop();
            this.eatingAnimationState.animateWhen(this.isHoldingFood(), this.tickCount);
            this.attackAnimationState.animateWhen(this.isAttacking(), this.tickCount);
        }
        // idle anim
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        // eat anim
        if (this.isHoldingFood()) {
            this.eatingAnimationState.start(this.tickCount);
            this.eatingAnimationState.startIfStopped(this.tickCount);
        } else {
            this.eatingAnimationState.stop();
        }
        if (!this.isHoldingFood()) {
            attackAnimationState.stop();
        }

        // flop anim
        if (!this.isInWater()) {
            if (this.idleAnimationState.isStarted())
                this.idleAnimationState.stop();
            if (this.eatingAnimationState.isStarted())
                this.eatingAnimationState.stop();
            this.floppingAnimationState.startIfStopped(this.tickCount);
        } else {
            this.floppingAnimationState.stop();
        }

        // attack anim
        if (attackAnimationTimeout <= 0 && this.isAttacking()) {

            attackAnimationTimeout = 20;
            attackAnimationState.start(this.tickCount);
        } else {

            --this.attackAnimationTimeout;
        }

        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        if (this.isInWater())
            super.updateWalkAnimation(pPartialTick);
    }

    // thank god for the camel class
    // used for positioning "riders" as "snared" targets.
    protected void positionRider(@Nonnull Entity pPassenger, @Nonnull Entity.MoveFunction pCallback) {
        int i = this.getPassengers().indexOf(pPassenger);
        if (i >= 0) {
            boolean flag = i == 0;
            float f = 0.5F;
            if (this.getPassengers().size() > 1) {
                if (!flag) {
                    f = -0.7F;
                }

                if (pPassenger instanceof Animal) {
                    f += 0.2F;
                }
            }

            Vec3 vec3 = (new Vec3(0.0D, 0.0D, f)).yRot(-this.yBodyRot * ((float) Math.PI / 180F));
            pCallback.accept(pPassenger, this.getX() + vec3.x * 1.3F, this.getY() - 0.2F, this.getZ() + vec3.z * 1.3F);
            //this.clampRotation(pPassenger);
        }
    }

    public boolean isDigesting() {
        return this.getEntityData().get(DIGESTING_ID);
    }

    public void setDigesting(boolean isDigesting) {
        this.entityData.set(DIGESTING_ID, isDigesting);
    }

    public boolean isAttacking() {
        return this.getEntityData().get(IS_ATTACKING);
    }

    public void setAttacking(boolean isAttacking) {
        this.entityData.set(IS_ATTACKING, isAttacking);
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean pFromBucket) {
        this.entityData.set(FROM_BUCKET, pFromBucket);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DIGESTING_ID, false);
        this.getEntityData().define(IS_ATTACKING, false);
        this.entityData.define(FROM_BUCKET, false);
    }

    public void addAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt(DIGESTING_TAG, this.isDigesting() ? this.ticksDigesting : -1);
        pCompound.putBoolean("FromBucket", this.fromBucket());
    }

    public void readAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFromBucket(pCompound.getBoolean("FromBucket"));
        if (pCompound.contains(DIGESTING_TAG, 99) && pCompound.getInt(DIGESTING_TAG) > -1) {
            this.startDigesting(pCompound.getInt(DIGESTING_TAG));
        }
    }

    private void startDigesting(int digestionTime) {
        this.ticksDigesting = digestionTime;
        this.setDigesting(true);
    }

    protected void digest() {
        // kills players, but discards entities to prevent item drops
        if (this.getFirstPassenger() != null) if (this.getFirstPassenger() instanceof Player) this.getFirstPassenger().kill(); else this.getFirstPassenger().discard();
        if (!this.isSilent()) {

            playMunchSound();
        }
        handleEntityEvent((byte) 7);
        this.ticksUntilHungry = HUNGER_COOLDOWN;
        setHungry(false);
        setHoldingFood(false);
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    public boolean isHoldingFood() {
        return isHoldingFood;
    }

    public void setHoldingFood(boolean holdingFood) {
        isHoldingFood = holdingFood;
    }

    @Nonnull
    public InteractionResult mobInteract(@Nonnull Player pPlayer, @Nonnull InteractionHand pHand) {
        if (pPlayer.getItemInHand(pHand).getItem() == Items.STICK && !pPlayer.level().isClientSide) {
            pPlayer.displayClientMessage(Component.literal(String.valueOf(this.isAttacking())), false);
            pPlayer.displayClientMessage(Component.literal(String.valueOf(this.isHoldingFood())), false);
            pPlayer.displayClientMessage(Component.literal(String.valueOf(this.getTarget())), false);
            pPlayer.displayClientMessage(Component.literal(String.valueOf(this.eatingAnimationState.isStarted())), false);
            return super.mobInteract(pPlayer, pHand);
        } else {

            return Bucketable.bucketMobPickup(pPlayer, pHand, this).orElse(super.mobInteract(pPlayer, pHand));
        }
    }

    public void saveToBucketTag(@Nonnull ItemStack pStack) {
        Bucketable.saveDefaultDataToBucketTag(this, pStack);
        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putInt("Age", this.getAge());
        compoundtag.putInt("TicksUntilHungry", this.ticksUntilHungry);
    }

    public void loadFromBucketTag(@Nonnull CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));
        }
        if (pTag.contains("TicksUntilHungry")) {
            this.ticksUntilHungry = (pTag.getInt("TicksUntilHungry"));
        }
    }

    @NotNull
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.ANOMALOCARIS_BUCKET.get());
    }

    @NotNull
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 7) {

            this.spawnParticles();
        } else

            super.handleEntityEvent(pId);
    }

    protected void spawnParticles() {
        for (int i = 0; i < 3; i++) {

            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.COD)), getX() + this.random.nextFloat() * .4f -.2f, getY() +.3f, getZ() + this.random.nextFloat() * .4f -.2f, 0, 0.01, (-0.3));
        }
    }

    public Anomalocaris getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.ANOMALOCARIS.get().create(pLevel);
    }

    public boolean isFood(ItemStack pStack) {
        return pStack.is(ItemTags.FISHES);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return this.random.nextInt(2) == 0 ? ModSounds.ANOMALOCARIS_HURT_1.get() : ModSounds.ANOMALOCARIS_HURT_2.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return this.random.nextInt(2) == 0 ? ModSounds.ANOMALOCARIS_DEATH_1.get() : ModSounds.ANOMALOCARIS_DEATH_2.get();
    }

    protected void playMunchSound() {
        this.level().playSound(null, this.getOnPos(), (this.random.nextInt(2) == 0 ? ModSounds.ANOMALOCARIS_EAT_1.get() : ModSounds.ANOMALOCARIS_EAT_2.get()) , SoundSource.NEUTRAL, getSoundVolume(), 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.7F;
    }

    public boolean doesHaveEggs() {
        return hasEggs;
    }

    public void setHasEggs(boolean hasEggs) {
        this.hasEggs = hasEggs;
    }

    /** Goals */
    static class PanicGoal extends net.minecraft.world.entity.ai.goal.PanicGoal {
        private final Anomalocaris entity;

        public PanicGoal(Anomalocaris entity, double modifier) {
            super(entity, modifier);
            this.entity = entity;
        }

        protected boolean shouldPanic() {
            return this.entity.isFreezing() || !this.entity.isInWater() || this.entity.isHoldingFood() || this.entity.isOnFire();
        }
    }

    static class DashToEnsnareGoal extends Goal {
        private final Anomalocaris entity;
        private LivingEntity target;

        public DashToEnsnareGoal(Anomalocaris entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            if (this.entity.isHoldingFood() || !this.entity.isHungry()) {
                return false;
            } else {
                this.target = this.entity.getTarget();
                if (this.target == null || this.target.isPassenger()) {
                    return false;
                } else {
                    double range = this.entity.distanceToSqr(this.target);
                    if (!(range < 1.0D) && !(range > 16.0D)) {

                        return this.entity.getRandom().nextInt(reducedTickDelay(5)) == 0;
                    } else {
                        return false;
                    }
                }
            }
        }

        public void start() {
            Vec3 vector = this.entity.getDeltaMovement();
            Vec3 ray = new Vec3(this.target.getX() - this.entity.getX(), this.target.getY() - this.entity.getY(), this.target.getZ() - this.entity.getZ());
            if (ray.lengthSqr() > 1.0E-7D) {
                ray = ray.normalize().scale(0.4D).add(vector.scale(0.2D));
            }

            this.entity.lookControl.setLookAt(target);
            this.entity.setDeltaMovement(ray.x, ray.y, ray.z);

            // if within range, and target is a smaller entity
            if (this.entity.getMeleeAttackRangeSqr(target) >= this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ())
                    && this.target.getBoundingBox().getSize() < this.entity.getBoundingBox().getSize()
                    && this.entity.isHungry() && !this.entity.isHoldingFood()) {

                // snatches it up
                target.setXRot(this.entity.getXRot());
                target.setYRot(this.entity.getYRot());
                target.startRiding(this.entity, true);
                this.entity.setHoldingFood(true);
                this.entity.setAttacking(true);
            }
        }
    }

    static class MeleeGoal extends MeleeAttackGoal {
        private final Anomalocaris mob;
        LivingEntity target;

        public MeleeGoal(Anomalocaris pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            this.mob = pMob;
            target = mob.getTarget();
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            // Only attacks entities its size and larger! Eats smaller ones.
            return super.canUse() && !this.mob.isHoldingFood() && this.mob.getTarget().getBoundingBox().getSize() > this.mob.getTarget().getBoundingBox().getSize();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            this.mob.setAttacking(true);
            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
        }
    }
    public static boolean checkSurfaceWaterDinoSpawnRules(EntityType<? extends Anomalocaris> pAquaticAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 13;
        return pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER) && AncientNatureConfig.PREHISTORIC_OVERWORLD_SPAWNING.get();
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final PathfinderMob entity;

        public SwimGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier, 10);
            this.entity = mob;
        }

        @Override
        public boolean canUse() {
            return this.entity.getTarget() == null && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.entity.getTarget() == null && super.canContinueToUse();
        }
    }

    /** MODIFIED TURTLE MATING GOALS */
    static class EggBreedGoal extends BreedGoal {
        private final Anomalocaris entity;

        public EggBreedGoal(Anomalocaris pAnimal, double pSpeedModifier) {
            super(pAnimal, pSpeedModifier);
            this.entity = pAnimal;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.entity.doesHaveEggs();
        }

        @Override
        protected void breed() {
            this.entity.setHasEggs(true);
            this.entity.setAge(6000);
            this.animal.resetLove();
            if (this.partner != null) this.partner.setAge(6000);
            if (this.partner != null) this.partner.resetLove();

            RandomSource randomsource = this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), randomsource.nextInt(7) + 1));
            }
        }
    }

    static class LayEggGoal extends MoveToBlockGoal {
        private final Anomalocaris entity;

        public LayEggGoal(Anomalocaris pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
            this.entity = pMob;
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            BlockState state = pLevel.getBlockState(pPos);
            BlockState below = pLevel.getBlockState(pPos.below());
            return !pLevel.canSeeSkyFromBelowWater(pPos) && state.is(Blocks.WATER) && below.isSolid() && !below.is(ModBlocks.ANOMALOCARIS_EGGS.get());
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.entity.doesHaveEggs();
        }

        public void tick() {
            super.tick();
            BlockPos entitypos = this.entity.blockPosition();
            if (this.entity.isInWater() && this.isReachedTarget()) {
                Level level = this.entity.level();
                level.playSound(null, entitypos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
                BlockState blockstate = ModBlocks.ANOMALOCARIS_EGGS.get().defaultBlockState();
                level.setBlock(blockPos, blockstate, 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this.entity, blockstate));
                this.entity.setHasEggs(false);
            }
        }
    }
}