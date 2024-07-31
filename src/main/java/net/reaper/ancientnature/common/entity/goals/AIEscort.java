package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.reaper.ancientnature.common.entity.ground.AbstractDinoAnimal;

import java.util.EnumSet;

public class AIEscort extends Goal
{
    private final AbstractDinoAnimal dragon;
    private BlockPos previousPosition;
    private final float maxRange = 2000F;

    public AIEscort(AbstractDinoAnimal entityIn, double movementSpeedIn) {
        this.dragon = entityIn;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.dragon.canMove() && this.dragon.getTarget() == null && this.dragon.getOwner() != null;
    }

    @Override
    public void tick() {
        if (this.dragon.getOwner() != null) {
            final float dist = this.dragon.distanceTo(this.dragon.getOwner());
            if (dist > maxRange) {
                return;
            }
            if (dist > this.dragon.getBoundingBox().getSize()) {
                if (previousPosition == null || previousPosition.distSqr(this.dragon.getOwner().blockPosition()) > 9) {
                    this.dragon.getNavigation().moveTo(this.dragon.getOwner(), 1F);
                    previousPosition = this.dragon.getOwner().blockPosition();
                }
            }
        }

    }

    @Override
    public boolean canContinueToUse() {
        return this.dragon.canMove() && this.dragon.getTarget() == null && this.dragon.getOwner() != null && this.dragon.getOwner().isAlive() && (this.dragon.distanceTo(this.dragon.getOwner()) > 15 || !this.dragon.getNavigation().isDone());
    }
}
