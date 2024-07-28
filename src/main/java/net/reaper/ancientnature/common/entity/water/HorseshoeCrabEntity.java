package net.reaper.ancientnature.common.entity.water;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.reaper.ancientnature.common.entity.goals.ai.SemiAquatic;
import net.reaper.ancientnature.core.init.ModEntities;
import org.jetbrains.annotations.Nullable;

public class HorseshoeCrabEntity extends AquaticAnimal implements SemiAquatic {

    public HorseshoeCrabEntity(EntityType<? extends HorseshoeCrabEntity> entityType, Level level) {
        super(entityType, level);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState stunnedAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;
    private int airSupply = 3600; // 6 minutes in ticks

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (level().isClientSide()) {
            this.idleAnimationState.animateWhen(!isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
        }
    }

    private void setupAnimationStates() {
        //Idle Animation
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return HorseshoeCrabEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 0.6F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(1, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreatheGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0F));

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isInWaterRainOrBubbleColumn()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F);
        } else if (!this.isInWaterRainOrBubbleColumn()){
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1F);
        }

        if (!this.isInWater()) {
            this.airSupply--;
            if (this.airSupply <= 0) {
                this.hurt(damageSources().drown(), Float.MAX_VALUE);
            }
        } else {
            this.airSupply = 3600; // Reset air supply when in water
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.SILVERFISH_HURT;
    }

    protected void playStepSound(BlockPos p_33804_, BlockState p_33805_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean shouldEnterWater() {
        return true;
    }

    @Override
    public boolean shouldLeaveWater() {
        return true;
    }
    @Override
    public boolean isInWaterRainOrBubbleColumn() {
        return true;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    @Override
    public int getWaterSearchRange() {
        return 32;
    }

    public static boolean canSpawn(EntityType<HorseshoeCrabEntity> tEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        return Animal.checkAnimalSpawnRules(tEntityType, serverLevelAccessor, spawnType, blockPos, randomSource) && !serverLevelAccessor.getLevelData().isRaining();
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

    private static class SwimGoal extends Goal {
        private final HorseshoeCrabEntity crab;

        public SwimGoal(HorseshoeCrabEntity crab) {
            this.crab = crab;
        }

        @Override
        public boolean canUse() {
            return this.crab.isInWater();
        }

        @Override
        public void tick() {
            this.crab.getNavigation().moveTo(this.crab.getX(), this.crab.getY(), this.crab.getZ(), 1.0D);
        }
    }
    private class BreatheGoal extends Goal {
        private final HorseshoeCrabEntity crab;

        public BreatheGoal(HorseshoeCrabEntity crab) {
            this.crab = crab;
        }

        @Override
        public boolean canUse() {
            return !this.crab.isInWater();
        }

        @Override
        public void tick() {
            if (this.crab.airSupply <= 0) {
                this.crab.hurt(damageSources().drown(), Float.MAX_VALUE);
            }
        }
    }
}