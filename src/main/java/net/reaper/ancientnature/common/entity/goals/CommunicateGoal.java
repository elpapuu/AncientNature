package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;

public class CommunicateGoal extends Goal {

    private final BaseTameableDinoEntity dino;
    public CommunicateGoal(BaseTameableDinoEntity pMob) {
        dino = pMob;
    }

    @Override
    public boolean canUse() {
//        if (dino.getComminicate() > 0) {
//            dino.setCommunicated(getCommunicate() - 1);
//            return false;
//        } else {
            return true;
//        }
    }

//
//    @Override
//    public void start() {
//        super.start();
//        this.target = this.dino.level().getNearestEntity(this.dino.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> {
//            return true;
//        }), this.targetConditions, this.dino, this.dino.getX(), this.dino.getEyeY(), this.dino.getZ());
//        if () {
//
//        }
//
//
//
//    }
//
//    @Override
//    public void stop() {
//        dino.setComminicate(1200);
//        super.stop();
//    }
}
