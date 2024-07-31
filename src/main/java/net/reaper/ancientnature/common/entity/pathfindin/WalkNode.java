package net.reaper.ancientnature.common.entity.pathfindin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;

public class WalkNode extends Node {
    private final BlockPos position;
    private final double penalty;

    public WalkNode(BlockPos position, double penalty) {
        super(position.getX(), position.getY(), position.getZ());
        this.position = position;
        this.penalty = penalty;
        this.type = BlockPathTypes.WALKABLE;
    }

    public BlockPos getPosition() {
        return position;
    }

    public double getPenalty() {
        return penalty;
    }
}
