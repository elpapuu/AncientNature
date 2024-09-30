package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;

import java.util.EnumSet;

public class CooldownAfterKillGoal extends Goal {
    private final AbstractTrexEntity entity;
    private final long cooldownTicks;
    private long lastKillTime;

    public CooldownAfterKillGoal(AbstractTrexEntity entity, long cooldownTicks) {
        this.entity = entity;
        this.cooldownTicks = cooldownTicks;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.lastKillTime = -cooldownTicks; // Initialize to allow immediate action.
    }

    @Override
    public boolean canUse() {
        long currentTime = entity.level().getGameTime();
        return currentTime - lastKillTime > cooldownTicks;
    }

    @Override
    public void stop() {
        long currentTime = entity.level().getGameTime();
        if (entity.getTarget() == null || !entity.getTarget().isAlive()) {
            lastKillTime = currentTime;
        }
    }

    @Override
    public void tick() {

    }
}
