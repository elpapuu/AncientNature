package net.reaper.ancientnature.common.entity.water;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.goals.SmallerEntityTargetGoal;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Anomalocaris extends WaterAnimal implements Bucketable {
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
    boolean isHolding;
    private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(Anomalocaris.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState floppingAnimationState = new AnimationState();
    public final AnimationState eatingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;

    public Anomalocaris(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new Anomalocaris.PanicGoal(2.0D));
        this.goalSelector.addGoal(2, new Anomalocaris.DashToEnsnareGoal(this));
        this.goalSelector.addGoal(3, new Anomalocaris.MeleeGoal(this, 1.2D, true));
        this.goalSelector.addGoal(4, new Anomalocaris.SwimGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Anomalocaris.class));
        this.targetSelector.addGoal(2, new SmallerEntityTargetGoal<>(this, WaterAnimal.class, false, p -> p.isInWater() && !p.isPassenger() && !(p instanceof Anomalocaris)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.MOVEMENT_SPEED, 1.2F)
                .add(Attributes.FOLLOW_RANGE, 4.0D);
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.9F;
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    @Override
    public void tick() {
        if (this.isAlive() && !this.isNoAi()) {
            if (this.level().isClientSide()) {

                setupAnimationStates();
            } else {
                if (this.getTarget() != null && !this.getTarget().isPassenger()) {

                    this.getLookControl().setLookAt(this.getTarget().getX(), this.getTarget().getEyeY(), this.getTarget().getZ());
                }
                if (!this.isHungry() && this.ticksUntilHungry == 0) {

                    setHungry(true);
                }
                if (this.isVehicle() && this.getFirstPassenger() instanceof WaterAnimal) {

                    this.getFirstPassenger().setXRot(this.getXRot());
                    this.getFirstPassenger().setYRot(this.getYRot());

                    if (this.isDigesting()) {
                        --this.ticksDigesting;
                        if (this.ticksDigesting < 0) {
                            this.digest();
                        } else if (this.ticksDigesting % 100 == 0) {

                            if (this.getFirstPassenger() instanceof LivingEntity target)
                                target.hurt(target.damageSources().mobAttack(this), target instanceof Player ? 5.0F : 0.0F);

                            if (!this.isSilent()) {

                                playMunchSound();
                            }
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

                // dolphin out of water behavior ig
                if (this.onGround()) {

                    this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F));
                    this.setYRot(this.random.nextFloat() * 360.0F);
                    this.setOnGround(false);
                    this.hasImpulse = true;
                }
            }
        }
        super.tick();
    }

    private void setupAnimationStates() {
        // idle anim
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        // eat or flop anim
        if (!this.isInWater()) {
            if (this.idleAnimationState.isStarted())
                this.idleAnimationState.stop();
            if (this.eatingAnimationState.isStarted())
                this.eatingAnimationState.stop();
            this.floppingAnimationState.startIfStopped(this.tickCount);
        } else {
            this.floppingAnimationState.stop();
            this.eatingAnimationState.animateWhen(this.isHolding, this.tickCount);
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
    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
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

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt(DIGESTING_TAG, this.isDigesting() ? this.ticksDigesting : -1);
        pCompound.putBoolean("FromBucket", this.fromBucket());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
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
        isHolding = false;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        return Bucketable.bucketMobPickup(pPlayer, pHand, this).orElse(super.mobInteract(pPlayer, pHand));
    }

    public void saveToBucketTag(ItemStack pStack) {
        Bucketable.saveDefaultDataToBucketTag(this, pStack);
        CompoundTag compoundtag = pStack.getOrCreateTag();
        //compoundtag.putInt("Age", this.getAge());
        Brain<?> brain = this.getBrain();
        if (brain.hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
            compoundtag.putLong("HuntingCooldown", brain.getTimeUntilExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN));
        }

    }

    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
        if (pTag.contains("Age")) {
            //this.setAge(pTag.getInt("Age"));
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

    /** Goals */
    class PanicGoal extends net.minecraft.world.entity.ai.goal.PanicGoal {
        public PanicGoal(double modifier) {
            super(Anomalocaris.this, modifier);
        }

        protected boolean shouldPanic() {
            return this.mob.isFreezing() || !this.mob.isInWater() || this.mob.isVehicle() || this.mob.isOnFire();
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
            if (this.entity.isVehicle() || !this.entity.isHungry()) {
                return false;
            } else {
                this.target = this.entity.getTarget();
                if (this.target == null || this.target.isPassenger()) {
                    return false;
                } else {
                    double range = this.entity.distanceToSqr(this.target);
                    if (!(range < 2.0D) && !(range > 16.0D)) {

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

            this.entity.setDeltaMovement(ray.x, ray.y, ray.z);

            // if within range, and target is a smaller entity
            if (this.entity.getMeleeAttackRangeSqr(target) >= this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ())
                    && this.target.getBoundingBox().getSize() < this.entity.getBoundingBox().getSize()) {

                // snatches it up
                target.setXRot(this.entity.getXRot());
                target.setYRot(this.entity.getYRot());
                target.startRiding(this.entity, true);
                this.entity.isHolding = true;
            }
        }
    }

    static class MeleeGoal extends MeleeAttackGoal {
        private final Anomalocaris mob;

        public MeleeGoal(Anomalocaris pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            this.mob = pMob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            // Only attacks entities its size and larger! Eats smaller ones.
            return super.canUse() && !this.mob.isVehicle() && this.mob.getTarget() != null
                    && this.mob.getTarget().getBoundingBox().getSize() > this.mob.getTarget().getBoundingBox().getSize();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return this.random.nextInt(2) == 0 ? ModSounds.ANOMALOCARIS_HURT_1.get() : ModSounds.ANOMALOCARIS_HURT_2.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return this.random.nextInt(2) == 0 ? ModSounds.ANOMALOCARIS_DEATH_1.get() : ModSounds.ANOMALOCARIS_DEATH_2.get();
    }

    protected void playMunchSound() {
        this.level().playSound(null, this.getOnPos(), (this.random.nextInt(2) == 0 ? ModSounds.ANOMALOCARIS_EAT_1.get() : ModSounds.ANOMALOCARIS_EAT_2.get()) , SoundSource.NEUTRAL, 2.0F, 1.0F);
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
}