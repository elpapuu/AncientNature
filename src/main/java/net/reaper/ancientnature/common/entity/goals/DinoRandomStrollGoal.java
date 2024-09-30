package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.TameableOrders;

public class DinoRandomStrollGoal extends WaterAvoidingRandomStrollGoal
{
    private final AbstractTrexEntity animal;
    public DinoRandomStrollGoal(AbstractTrexEntity pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
        this.animal = pMob;
    }

    @Override
    public boolean canUse() {
        return super.canUse()&&
                !animal.isSleeping()&&animal.getSmartPose()== SmartAnimalPose.IDLE
                && !animal.isSitting()&&animal.getOrder()!= TameableOrders.SIT;
    }

}
