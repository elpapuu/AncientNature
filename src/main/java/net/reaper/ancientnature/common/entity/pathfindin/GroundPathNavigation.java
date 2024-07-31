package net.reaper.ancientnature.common.entity.pathfindin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GroundPathNavigation extends PathNavigation {
    public GroundPathNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new WalkNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean canUpdatePath() {
        return this.mob.onGround() || this.isInLiquid() || this.mob.isPassenger();
    }

    @Override
    protected Vec3 getTempMobPos() {
        return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
    }

    @Override
    protected boolean isInLiquid() {
        return this.mob.isInWaterOrBubble() || this.mob.isInLava();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isDone()) {
            BlockPos targetPos = this.path.getTarget();
            double speed = this.mob.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED);
            this.mob.getMoveControl().setWantedPosition(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
        }
    }

    @Override
    public Path createPath(Set<BlockPos> targetPos, int range, boolean canOpenDoors, int maxVisitedNodes) {
        return super.createPath(targetPos, range, canOpenDoors, maxVisitedNodes);
    }

    @Override
    public Path createPath(BlockPos pos, int range, int maxVisitedNodes) {
        return this.createPath(Set.of(pos), range, false, maxVisitedNodes);
    }
}
