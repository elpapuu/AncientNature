package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;

import java.util.EnumSet;

public class RushTargetGoal extends Goal {

    private final AbstractTrexEntity mob;
    private LivingEntity target;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int delayCounter;

    public RushTargetGoal(AbstractTrexEntity mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.navigation = mob.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.mob.getTarget();
        return this.target != null && !mob.isSleeping() && !mob.isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && !this.navigation.isDone() && this.mob.distanceToSqr(this.target) > 2.0D;
    }

    @Override
    public void start() {
        this.delayCounter = 0;
    }

    @Override
    public void tick() {
        if (this.target != null) {
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            double distance = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());

            if (distance > 2.0D) {
                this.navigation.moveTo(this.target, this.speedModifier);
            } else {
                this.mob.doHurtTarget(this.target);
            }

            this.delayCounter = Math.max(this.delayCounter - 1, 0);
        }
    }

    @Override
    public void stop() {
        this.target = null;
        this.navigation.stop();
    }
}
