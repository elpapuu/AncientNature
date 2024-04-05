package net.reaper.ancientnature.common.entity.water;

import com.mojang.math.Axis;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.reaper.ancientnature.common.entity.goals.AvoidEntitySprinting;
import net.reaper.ancientnature.common.entity.goals.FishSwimGoal;
import net.reaper.ancientnature.common.entity.goals.PanicSprintingGoal;
import net.reaper.ancientnature.core.init.ModItems;
import org.joml.*;

public class ArandaspisEntity extends AbstractFish {
    public final AnimationState flopAnimation = new AnimationState();
    public final AnimationState idleAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;

    public ArandaspisEntity(EntityType<? extends AbstractFish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicSprintingGoal(this, 4f));
        this.goalSelector.addGoal(2, new AvoidEntitySprinting<>(this, Player.class, 8.0F,1f, 4f, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(4, new FishSwimGoal(this));
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

    @Override
    protected float getWaterSlowDown() {
        return 1f;
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        super.travel(pTravelVector);
        if (!this.level().isClientSide) {
            this.setSharedFlag(8, this.getLastHurtByMob() != null && isInWater());
            if (getSharedFlag(8)) {
                this.level().broadcastEntityEvent(this, (byte) 7);
            }
        }
    }

    public boolean isPanicing(){
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

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 1.3d)
                .add(Attributes.FOLLOW_RANGE, 25d);

    }

    @Override
    public ItemStack getBucketItemStack() {
        return ModItems.ARANDASPIS_BUCKET.get().getDefaultInstance();
    }
}
