package net.reaper.ancientnature.common.entity.water;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.goals.AvoidEntitySprinting;
import net.reaper.ancientnature.common.entity.goals.ParanogmiusJumpGoal;
import net.reaper.ancientnature.common.entity.goals.PanicSprintingGoal;
import net.reaper.ancientnature.common.entity.goals.WildBreedGoal;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModEntities;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class Paranogmius extends AquaticAnimal {
    public final AnimationState flopAnimation = new AnimationState();
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;

    boolean hasEggs;
    private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(Paranogmius.class, EntityDataSerializers.BOOLEAN);

    public Paranogmius(EntityType<? extends AquaticAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SmoothSwimmingMoveControl(this, 130, 35, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(0, new SwimGoal(this, 4f));
        this.goalSelector.addGoal(0, new PanicSprintingGoal(this, 2.0D));
        this.goalSelector.addGoal(0, new PanicSprintingGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new AvoidEntitySprinting<>(this, Player.class, 8.0F,1f, 2f, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new EggBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new ParanogmiusJumpGoal(this, 10));
        this.goalSelector.addGoal(3, new LayEggGoal(this, 1.0D, 16, 16));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 4, 4));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FollowBoatGoal(this));
        this.goalSelector.addGoal(20, new WildBreedGoal(this, Entity::isInWater, 300));
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.PARANOGMIUS.get().create(pLevel);
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
            if (this.onGround()) {
                this.setDeltaMovement(this.getDeltaMovement().add((double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5, (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)));
                this.setYRot(this.random.nextFloat() * 120.0F);
                this.setOnGround(false);
                this.hasImpulse = true;

            }
            //if (isInLove()) setDeltaMovement(this.getDeltaMovement().x / 1.5, this.getDeltaMovement().y, this.getDeltaMovement().z / 1.5);
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimation.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        if (!this.isInWater()) {
            if (this.idleAnimation.isStarted()) this.idleAnimation.stop();
            if (this.attackAnimation.isStarted()) this.attackAnimation.stop();
            this.flopAnimation.startIfStopped(this.tickCount);
        } else {
            this.flopAnimation.stop();
        }
        // idle anim
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimation.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
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
                .add(Attributes.MAX_HEALTH, 28.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D);
    }

    protected float getWaterSlowDown() {
        return 1f;
    }
    public int getMaxHeadXRot() {
        return 1;
    }

    public int getMaxHeadYRot() {
        return 1;
    }

    protected boolean canRide(Entity pEntity) {
        return true;
    }
    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.001, 0.0));
            }
        } else {
            super.travel(pTravelVector);
        }

    }

    public boolean doesHaveEggs() {
        return hasEggs;
    }

    public void setHasEggs(boolean hasEggs) {
        this.hasEggs = hasEggs;
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final Paranogmius entity;

        public SwimGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier, 3);
            this.entity = (Paranogmius) mob;
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

    public static boolean checkWaterSurfaceSpawnRules(EntityType<? extends AquaticAnimal> aquatic, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }
    static class EggBreedGoal extends BreedGoal {
        private final Paranogmius entity;

        public EggBreedGoal(Paranogmius pAnimal, double pSpeedModifier) {
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
        private final Paranogmius entity;

        public LayEggGoal(Paranogmius pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
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
                BlockState blockstate = ModBlocks.ARANDASPIS_ROE.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN);
                level.setBlock(blockPos, blockstate, 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this.entity, blockstate));
                this.entity.setHasEggs(false);
            }
        }
    }
}
