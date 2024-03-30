package net.reaper.ancientnature.common.entity.water;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.reaper.ancientnature.core.init.ModItems;

public class ArandaspisEntity extends AbstractFish {
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimation = new AnimationState();
    private int swimAnimationTimeout = 0;

    public ArandaspisEntity(EntityType<? extends AbstractFish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        //flopping condition
        if (!this.isInWater() && this.onGround() && this.verticalCollision){
            this.flopAnimation.startIfStopped(this.tickCount);
        }else {
            this.flopAnimation.stop();
            if (this.swimAnimationTimeout <= 0) {
                this.swimAnimationTimeout = this.random.nextInt(40) + 80;
                this.swimAnimationState.start(this.tickCount);
            } else {
                --this.swimAnimationTimeout;
            }
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING && !this.flopAnimation.isStarted()) {
            f = Math.min(pPartialTick * 6f, 1f);
        } else {
            f = 0f;
        }
        this.swimAnimationState.updateTime(pPartialTick, f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FOLLOW_RANGE, 25d);

    }

    @Override
    public ItemStack getBucketItemStack() {
        return ModItems.ARANDASPIS_BUCKET.get().getDefaultInstance();
    }
}
