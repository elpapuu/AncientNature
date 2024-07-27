package net.reaper.ancientnature.common.entity.smartanimal.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;

public class LookAtPlayerGoal extends net.minecraft.world.entity.ai.goal.LookAtPlayerGoal {
    SmartAnimatedAnimal animal;
    public LookAtPlayerGoal(SmartAnimatedAnimal pMob, float pLookDistance) {
        super(pMob, Player.class, pLookDistance);
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
