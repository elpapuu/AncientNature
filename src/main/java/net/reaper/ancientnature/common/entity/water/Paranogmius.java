package net.reaper.ancientnature.common.entity.water;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.reaper.ancientnature.common.entity.goals.AvoidEntitySprinting;
import net.reaper.ancientnature.common.entity.goals.PanicSprintingGoal;
import net.reaper.ancientnature.common.entity.goals.WildBreedGoal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class Paranogmius extends AquaticAnimal implements Bucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Paranogmius.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState flopAnimation = new AnimationState();
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    boolean hasEggs;

    public Paranogmius(EntityType<? extends AquaticAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SmoothSwimmingMoveControl(this, 130, 35, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicSprintingGoal(this, 4f));
        this.goalSelector.addGoal(1, new AvoidEntitySprinting<>(this, Player.class, 8.0F,1f, 2f, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(20, new WildBreedGoal(this, Entity::isInWater, 300));
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
    @Nonnull
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.isPassenger();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        } else {

            //if (isInLove()) setDeltaMovement(this.getDeltaMovement().x / 1.5, this.getDeltaMovement().y, this.getDeltaMovement().z / 1.5);
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimation.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        //flopping condition
        if (!this.isInWater()) {
            if (this.idleAnimation.isStarted())
                this.idleAnimation.stop();
            this.flopAnimation.startIfStopped(this.tickCount);
        } else {
            this.flopAnimation.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        if (this.isInWater())
            super.updateWalkAnimation(pPartialTick);
    }

    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.4F, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();
    }

    @Nonnull
    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);

    }

    protected float getWaterSlowDown() {
        return 1f;
    }

    protected float getStandingEyeHeight(@Nonnull Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.5F;
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        super.travel(pTravelVector);
        if (!this.level().isClientSide) {
            this.setSharedFlag(8, this.getLastHurtByMob() != null && isInWater());
            if (getSharedFlag(8)) {
                this.level().broadcastEntityEvent(this, (byte) 7);
            }
        }
    }

    static class FishSwimGoal extends RandomSwimmingGoal {
        private final Paranogmius fish;

        public FishSwimGoal(Paranogmius pFish) {
            super(pFish, 1.0D, 40);
            this.fish = pFish;
        }

        public boolean canUse() {
            return super.canUse() && fish.isInWater();
        }
    }

    @Override
    public boolean fromBucket() {
        return false;
    }

    @Override
    public void setFromBucket(boolean b) {

    }

    @Override
    public void saveToBucketTag(ItemStack itemStack) {

    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {

    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    @Override
    public SoundEvent getPickupSound() {
        return null;
    }

    @Override
    public LivingEntity self() {
        return super.self();
    }

    @Override
    public boolean shouldRiderSit() {
        return super.shouldRiderSit();
    }

    @Override
    public boolean canRiderInteract() {
        return super.canRiderInteract();
    }

    @Override
    public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
        return super.canBeRiddenUnderFluidType(type, rider);
    }

    @Override
    public boolean isInFluidType(FluidState state) {
        return super.isInFluidType(state);
    }

    @Override
    public boolean canStartSwimming() {
        return super.canStartSwimming();
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return super.isPushedByFluid(type);
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return super.canSwimInFluidType(type);
    }

    @Override
    public boolean canHydrateInFluidType(FluidType type) {
        return super.canHydrateInFluidType(type);
    }

    @Override
    public boolean hasCustomOutlineRendering(Player player) {
        return super.hasCustomOutlineRendering(player);
    }

    @Override
    public boolean shouldUpdateFluidWhileBoating(FluidState state, Boat boat) {
        return super.shouldUpdateFluidWhileBoating(state, boat);
    }

    @Override
    public void sinkInFluid(FluidType type) {
        super.sinkInFluid(type);
    }

    @Override
    public boolean moveInFluid(FluidState state, Vec3 movementVector, double gravity) {
        return super.moveInFluid(state, movementVector, gravity);
    }
}
