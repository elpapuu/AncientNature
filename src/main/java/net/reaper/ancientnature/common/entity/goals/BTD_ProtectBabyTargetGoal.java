package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;
import net.reaper.ancientnature.common.entity.ground.OviraptorEntity;

public class BTD_ProtectBabyTargetGoal extends NearestAttackableTargetGoal {

    private boolean Baby = false ;

    private BaseTameableDinoEntity living;

    public BTD_ProtectBabyTargetGoal(Mob pMob, Class pTargetType, boolean pMustSee) {
        super(pMob, pTargetType, pMustSee);
        living = (BaseTameableDinoEntity) pMob;

    }

    public boolean canUse() {
        if (mob instanceof BaseTameableDinoEntity) {
           Baby = mob.isBaby();
        }
        if (mob instanceof OviraptorEntity && ((OviraptorEntity) mob).isDistracted()) {
            return false;
        }
        if (Baby) {
            return false;
        } else {
            if (super.canUse()) {
                for(BaseTameableDinoEntity dino : living.level().getEntitiesOfClass(living.getClass(), living.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
                    if (dino.isBaby()) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    protected double getFollowDistance() {
        return super.getFollowDistance() * 0.5D;
    }
}
