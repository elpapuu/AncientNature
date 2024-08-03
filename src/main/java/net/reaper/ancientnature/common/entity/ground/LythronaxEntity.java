package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.util.AnimalDiet;
import net.reaper.ancientnature.common.messages.NetworkHandler;
import net.reaper.ancientnature.common.network.packet.EntityAttackKeyPacket;
import net.reaper.ancientnature.common.util.EntityUtils;

public class LythronaxEntity extends SmartAnimatedAnimal {

    public LythronaxEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public float tailRot;
    public float prevTailRot;

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 95)
                .add(Attributes.MOVEMENT_SPEED, 0.25d)
                .add(Attributes.FOLLOW_RANGE, 64d)
                .add(Attributes.ATTACK_KNOCKBACK, 1d)
                .add(Attributes.ATTACK_DAMAGE,3.0);

    }
    @Override
    public void tick() {
        super.tick();

        this.prevTailRot = this.tailRot;
        this.tailRot += (-(this.yBodyRot - this.yBodyRotO) - this.tailRot) * 0.15f;
    }


    @Override
    public void defineDiet(AnimalDiet.Builder pDietBuilder) {
        super.defineDiet(pDietBuilder);
        pDietBuilder.addItems(Items.BEEF,Items.PORKCHOP);
    }

    @Override
    public int getAttackAnimationDamageDelay() {
        return 6;
    }

    @Override
    public int getEatAnimationConsumeDelay() {
        return 20;
    }


    @Override
    protected int getAnimationLengthInTicks(SmartAnimalPose smartPose) {
        return switch(smartPose)
                {
                    case IDLE ->(int)(1.92*20.0);
                    case WALK -> (int)(1.92*20.0);
                    case RUN -> (int)(.84*20.0);
                    case EAT -> (int)(2.84*20.0);
                    case ATTACK -> (int)(.72*20.0);
                    case ATTACK2 -> 0;
                    case ATTACK3 -> 0;
                    case ROAR -> 0;
                    case DOWN -> (int)(.96*20.0);
                    case FALL_ASLEEP -> (int)(.88*20.0);
                    case WAKE_UP -> (int)(1.04*20.0);
                    case UP -> (int)(1.68*20.0);
                    case SIT -> (int)(1.92*20.0);
                    case SLEEP -> (int)(1.92*20.0);
                    case SWIM -> (int)(1.92*20.0);
                    case COMMUNICATE -> (int)(2.72*20.0);
                };
    }


    @Override
    public boolean isRidable() {
        return true;
    }

}
