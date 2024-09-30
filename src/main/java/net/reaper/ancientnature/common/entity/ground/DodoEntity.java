package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.util.AnimalDiet;

public class DodoEntity extends SmartAnimatedAnimal {

    public DodoEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.FOLLOW_RANGE, 64d)
                .add(Attributes.ATTACK_DAMAGE, 1);
    }


    @Override
    public void defineDiet(AnimalDiet.Builder pDietBuilder) {
        super.defineDiet(pDietBuilder);
        pDietBuilder.addItems(Items.MELON, Items.GLISTERING_MELON_SLICE, Items.MELON_SLICE, Items.MELON_SEEDS, Items.SWEET_BERRIES, Items.GLOW_BERRIES);
    }

    @Override
    protected int getAnimationLengthInTicks(SmartAnimalPose smartPose) {
        return switch (smartPose){
            case IDLE -> (int)(2.88F*20.0);
            case WALK -> (int)(0.96F*20.0);
            case RUN -> (int)(0.72F*20.0);
            case SWIM -> (int)(1.28F*20.0);
            case COMMUNICATE -> (int)(0.5417F*20.0);
            case EAT -> (int)(2.12F*20.0);
            case ATTACK -> (int)(.72*20.0);
            case ATTACK2 -> 0;
            case ATTACK3 -> 0;
            case ROAR -> 0;
            case DOWN -> (int)(0.96F*20.0);
            case FALL_ASLEEP -> (int)(0.68F*20.0);
            case WAKE_UP -> (int)(0.72F*20.0);
            case SNIFF -> 0;
            case INTIMATED -> 0;
            case UP -> (int)(0.64F*20.0);
            case SIT -> (int)(2.88F*20.0);
            case SLEEP -> (int)(2.88F*20.0);
        };

    }

    @Override
    public int getEatAnimationConsumeDelay() {
        return 40;
    }

    @Override
    public boolean canAttack() {
        return false;
    }
}
