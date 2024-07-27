package net.reaper.ancientnature.common.entity.smartanimal.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.smartanimal.TameableOrders;

public class RandomWanderStrollGoal extends WaterAvoidingRandomStrollGoal {
    SmartAnimatedAnimal animal;
    public RandomWanderStrollGoal(SmartAnimatedAnimal pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
        animal = pMob;
    }

    @Override
    public boolean canUse() {
        return super.canUse()&&
                !animal.isSleeping()&&!animal.shouldSleep()&&animal.getSmartPose()== SmartAnimalPose.IDLE
                && !animal.isSitting()&&animal.getOrder()!= TameableOrders.SIT;
    }


}
