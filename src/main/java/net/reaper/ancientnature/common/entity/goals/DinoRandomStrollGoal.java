package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.reaper.ancientnature.common.entity.ground.AbstractDinoAnimal;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.TameableOrders;
import net.reaper.ancientnature.common.util.AStar;

import java.util.List;
import java.util.Objects;

public class DinoRandomStrollGoal extends WaterAvoidingRandomStrollGoal
{
    private final AbstractDinoAnimal mob;
    public DinoRandomStrollGoal(AbstractDinoAnimal pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
        this.mob = pMob;
    }

    @Override
    public boolean canUse() {
        return super.canUse()&&
                !mob.isSleeping()&&mob.getSmartPose()== SmartAnimalPose.IDLE
                && !mob.isSitting()&&mob.getOrder()!= TameableOrders.SIT;
    }

}
