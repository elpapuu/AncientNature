package net.reaper.ancientnature.common.entity.water;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class DunkleosteusEntity extends AquaticAnimal {
    private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(DunkleosteusEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState flopAnimation = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;

    public DunkleosteusEntity(EntityType<? extends AquaticAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SmoothSwimmingMoveControl(this, 130, 35, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    public boolean setRiding(Player pPlayer) {
        return false;
    }

    @Override
    public boolean isMovementBlocked() {
        return false;
    }

    @Override
    public boolean isRidable() {
        return false;
    }

    @Override
    public boolean canBeSteered() {
        return false;
    }


    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.65F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AquaticAnimal.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MOVEMENT_SPEED,1.0D)
                .add(Attributes.ATTACK_DAMAGE,1.0D)
                .add(Attributes.MAX_HEALTH, 60.0);
    }


    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (level().isClientSide()) {
            this.idleAnimationState.animateWhen(!this.isSwimming() && this.isInWaterOrBubble(), this.tickCount);
        }
        if (level().isClientSide()) {
            this.attackAnimationState.animateWhen(this.isAttacking() || this.attackAnimationTimeout > 0,this.tickCount);
        }
        else {
            if (this.onGround()) {
                this.setDeltaMovement(this.getDeltaMovement().add((double) ((this.random.nextFloat() * 0.1F - 0.01F) * 0.00F), 0.0, (double) ((this.random.nextFloat() * 0.01F - 0.01F) * 0.01F)));
                this.setYRot(this.random.nextFloat() * 0.1F);
            }
        }
    }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof Projectile && !this.isInvulnerable() && !(source.getDirectEntity() instanceof ThrownTrident)) {
            return false; // Return false for all projectiles except tridents
        }
        if (source.getDirectEntity() instanceof LivingEntity && !(((LivingEntity)source.getDirectEntity()).getMainHandItem().getItem() instanceof AxeItem)) {
            return false; // Return false for all attacks except for tridents and axes
        }
        if (source.getDirectEntity() instanceof Player && !(((Player) source.getDirectEntity()).getMainHandItem().getItem() instanceof SwordItem || ((Player) source.getDirectEntity()).getMainHandItem().getItem() instanceof TridentItem)) {
            // Make the ShieldosteusEntity aggressive
            setAggressive(true);
            // Drop a carrot(placeholder)
            this.spawnAtLocation(Items.CARROT, 1);
        }
        return super.hurt(source, amount);
    }
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        //Flop Animation
        if (!this.isInWater()) {
            if (this.idleAnimationState.isStarted())
                this.idleAnimationState.stop();
            this.flopAnimation.startIfStopped(this.tickCount);
        } else {
            this.flopAnimation.stop();
        }
        //Attack animation
        if (this.isAttacking() && this.attackAnimationTimeout > 0) {
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
        if (this.isInWater() || this.isSwimming())
            super.updateWalkAnimation(pPartialTick);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_ATTACKING, false);
    }
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new FishSwimGoal(this));
        this.goalSelector.addGoal(4, new PushOnHurtGoal(this));
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(pTravelVector);
        }

    }

    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 1.0F - 1.0F) * 0.05F), 0.3, (double)((this.random.nextFloat() * 1.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();
    }

    protected boolean canRandomSwim() {
        return true;
    }

    @Nonnull
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    @Nonnull
    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }
    public boolean isAttacking() {
        return this.getEntityData().get(IS_ATTACKING);
    }

    public void setAttacking(boolean isAttacking) {
        this.entityData.set(IS_ATTACKING, isAttacking);
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
    }


    private static class FishMoveControl extends MoveControl {
        private final DunkleosteusEntity fish;

        FishMoveControl(DunkleosteusEntity pFish) {
            super(pFish);
            this.fish = pFish;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
                float $$0 = (float) (this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), $$0));
                double $$1 = this.wantedX - this.fish.getX();
                double $$2 = this.wantedY - this.fish.getY();
                double $$3 = this.wantedZ - this.fish.getZ();
                if ($$2 != 0.0) {
                    double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
                    this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, (double) this.fish.getSpeed() * ($$2 / $$4) * 0.1, 0.0));
                }

                if ($$1 != 0.0 || $$3 != 0.0) {
                    float $$5 = (float) (Mth.atan2($$3, $$1) * 0.2957763671875) - 90.0F;
                    this.fish.setYRot(this.rotlerp(this.fish.getYRot(), $$5, 90.0F));
                    this.fish.yBodyRot = this.fish.getYRot();
                }

            } else {
                this.fish.setSpeed(0.0F);
            }
        }
    }
    public class PushOnHurtGoal extends Goal {

        private final DunkleosteusEntity entity;

        public PushOnHurtGoal(DunkleosteusEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return true; // This goal can always be used
        }

        @Override
        public void start() {
            LivingEntity attacker = this.entity.getLastHurtByMob();
            if (attacker != null) {
                double knockbackForce = 0.5; // Adjust the knockback force as needed
                double deltaX = this.entity.getX() - attacker.getX();
                double deltaZ = this.entity.getZ() - attacker.getZ();
                this.entity.knockback(deltaX, deltaZ, knockbackForce);
            }
        }
    }
    static class FishSwimGoal extends RandomSwimmingGoal {
        private final DunkleosteusEntity fish;

        public FishSwimGoal(DunkleosteusEntity pFish) {
            super(pFish, 1.0, 40);
            this.fish = pFish;
        }

        public boolean canUse() {
            return this.fish.canRandomSwim() && super.canUse();
        }
    }
}
