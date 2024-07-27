package net.reaper.ancientnature.common.entity.smartanimal.ai.goal;

import net.minecraft.world.entity.Mob;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;

public class RandomLookAroundGoal extends net.minecraft.world.entity.ai.goal.RandomLookAroundGoal {
    SmartAnimatedAnimal animal;
    public RandomLookAroundGoal(SmartAnimatedAnimal pMob) {
        super(pMob);
        animal = pMob;
    }

    @Override
    public boolean canUse() {
        return super.canUse()&&!animal.isSleeping()&&animal.getSmartPose()!= SmartAnimalPose.SLEEP;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse()&&!animal.isSleeping()&&animal.getSmartPose()!= SmartAnimalPose.SLEEP;
    }
}
