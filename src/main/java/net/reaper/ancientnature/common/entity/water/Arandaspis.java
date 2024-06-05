package net.reaper.ancientnature.common.entity.water;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.goals.AvoidEntitySprinting;
import net.reaper.ancientnature.common.entity.goals.PanicSprintingGoal;
import net.reaper.ancientnature.common.entity.goals.WildBreedGoal;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModEntities;
import net.reaper.ancientnature.core.init.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class Arandaspis extends AquaticAnimal implements Bucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Arandaspis.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState flopAnimation = new AnimationState();
    public final AnimationState idleAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    boolean hasEggs;

    public Arandaspis(EntityType<? extends AquaticAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SmoothSwimmingMoveControl(this, 130, 35, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicSprintingGoal(this, 4f));
        this.goalSelector.addGoal(1, new AvoidEntitySprinting<>(this, Player.class, 8.0F,1f, 2f, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new Arandaspis.EggBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new Arandaspis.LayEggGoal(this, 1.0D, 16, 16));
        this.goalSelector.addGoal(4, new FishSwimGoal(this));
        this.goalSelector.addGoal(20, new WildBreedGoal(this, Entity::isInWater, 300));
    }

    // overridden to apply extra condition for WildBreedGoal
    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.isPassenger();
    }

    @Nonnull
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.ARANDASPIS.get().create(pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        } else {
            if (this.onGround()) {
                this.setDeltaMovement(this.getDeltaMovement().add((double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5, (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)));
                this.setYRot(this.random.nextFloat() * 360.0F);
                this.setOnGround(false);
                this.hasImpulse = true;
            }

            //if (isInLove()) setDeltaMovement(this.getDeltaMovement().x / 1.5, this.getDeltaMovement().y, this.getDeltaMovement().z / 1.5);
        }
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
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);

    }

    @Override
    protected float getWaterSlowDown() {
        return 1f;
    }

    protected float getStandingEyeHeight(@Nonnull Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.5F;
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(pTravelVector);
        }

    }

    public boolean isPanicking(){
        return this.getSharedFlag(8);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 7) {
            this.spawnBubbles();
        } else
            super.handleEntityEvent(pId);
    }

    protected void spawnBubbles() {
        for (int i = 0; i < 3; i++) {
            this.level().addParticle(ParticleTypes.BUBBLE, getX() + this.random.nextFloat() * .4f -.2f, getY() +.3f, getZ() + this.random.nextFloat() * .4f -.2f, 0, 0.01, (-0.3));
        }
    }

    @Override
    public void setSprinting(boolean pSprinting) {
        this.setSharedFlag(3, pSprinting);
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

    @Nonnull
    public InteractionResult mobInteract(@Nonnull Player pPlayer, @Nonnull InteractionHand pHand) {
        return Bucketable.bucketMobPickup(pPlayer, pHand, this).orElse(super.mobInteract(pPlayer, pHand));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean pFromBucket) {
        this.entityData.set(FROM_BUCKET, pFromBucket);
    }

    @Override
    public void saveToBucketTag(@Nonnull ItemStack pStack) {
        Bucketable.saveDefaultDataToBucketTag(this, pStack);
        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putInt("Age", this.getAge());
    }

    @Override
    public void loadFromBucketTag(@Nonnull CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
        if (pTag.contains("Age")) {
            this.setAge(pTag.getInt("Age"));
        }
    }

    @Override
    @Nonnull
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.ARANDASPIS_BUCKET.get());
    }

    @Override
    @Nonnull
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    public boolean doesHaveEggs() {
        return hasEggs;
    }

    public void setHasEggs(boolean hasEggs) {
        this.hasEggs = hasEggs;
    }

    /** NON-PUBLIC FISH AI */
    static class FishSwimGoal extends RandomSwimmingGoal {
        private final Arandaspis fish;

        public FishSwimGoal(Arandaspis pFish) {
            super(pFish, 1.0D, 40);
            this.fish = pFish;
        }

        public boolean canUse() {
            return super.canUse() && fish.isInWater();
        }
    }

    /** MODIFIED TURTLE MATING GOALS */
    static class EggBreedGoal extends BreedGoal {
        private final Arandaspis entity;

        public EggBreedGoal(Arandaspis pAnimal, double pSpeedModifier) {
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
        private final Arandaspis entity;

        public LayEggGoal(Arandaspis pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
            this.entity = pMob;
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            BlockState state = pLevel.getBlockState(pPos);
            BlockState below = pLevel.getBlockState(pPos.below());
            return !pLevel.canSeeSkyFromBelowWater(pPos) && state.is(Blocks.WATER) && below.isSolid() && !below.is(ModBlocks.ARANDASPIS_ROE.get());
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.entity.doesHaveEggs();
        }

        public void tick() {
            super.tick();
            BlockPos entitypos = this.entity.blockPosition();
            if (this.entity.isInWater() && this.isReachedTarget()) {
                Level level = this.entity.level();
                level.playSound(null, entitypos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, this.entity.getSoundVolume(), 1.0F);
                BlockState blockstate = ModBlocks.ARANDASPIS_ROE.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN);
                level.setBlock(blockPos, blockstate, 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this.entity, blockstate));
                this.entity.setHasEggs(false);
            }
        }
    }
}
