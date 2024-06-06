package net.reaper.ancientnature.common.entity.goals;



import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;

public abstract class CustomMoveToBlockGoal extends Goal {
    private static final int GIVE_UP_TICKS = 1200;
    private static final int STAY_TICKS = 1200;
    private static final int INTERVAL_TICKS = 200;
    protected final PathfinderMob mob;
    public final double speedModifier;
    /** Controls task execution delay */
    protected int nextStartTick;
    protected int tryTicks;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos blockPos = BlockPos.ZERO;
    private boolean reachedTarget;
    private final int searchRange;
    private final int verticalSearchRange;
    protected int verticalSearchStart;

    public CustomMoveToBlockGoal(PathfinderMob pMob, double pSpeedModifier, int pSearchRange) {
        this(pMob, pSpeedModifier, pSearchRange, 1);
    }

    public CustomMoveToBlockGoal(PathfinderMob pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
        this.mob = pMob;
        this.speedModifier = pSpeedModifier;
        this.searchRange = pSearchRange;
        this.verticalSearchStart = 0;
        this.verticalSearchRange = pVerticalSearchRange;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        System.out.println("Has Reached target: " + reachedTarget);
        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            return this.findNearestBlock();
        }
    }

    protected int nextStartTick(PathfinderMob pCreature) {
        return reducedTickDelay(200 + pCreature.getRandom().nextInt(200));
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.tryTicks >= -this.maxStayTicks && this.tryTicks <= 1200 && this.isValidTarget(this.mob.level(), this.blockPos);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.moveMobToBlock();
        this.tryTicks = 0;
        this.maxStayTicks = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200) + 1200) + 1200;
    }

    protected void moveMobToBlock() {
        this.mob.getNavigation().moveTo((double)((float)this.blockPos.getX()) + 0.5D, (double)(this.blockPos.getY())+ 0.5D, (double)((float)this.blockPos.getZ()) + 0.5D, this.speedModifier);
    }

    public double acceptedDistance() {
        return Math.pow(mob.getBbWidth(), 2) + 3.0F;
    }
    protected BlockPos getMoveToTarget() {
        return this.blockPos.above();
    }

    protected BlockPos getTarget() {
        return this.blockPos;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        BlockPos blockpos = this.getMoveToTarget();
        double distance = this.mob.distanceToSqr(this.blockPos.getX() + 0.5F, this.blockPos.getY() + 0.5F, this.blockPos.getZ() + 0.5F);
        if (distance < 3) {
            mob.getNavigation().stop();
            this.reachedTarget = true;
            --this.tryTicks;
        } else {
            this.reachedTarget = false;
            ++this.tryTicks;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo((double)((float)blockpos.getX()) + 0.5D, (double)blockpos.getY() + 0.5, (double)((float)blockpos.getZ()) + 0.5D, this.speedModifier);
            }
        }

    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 40 == 0;
    }

    protected boolean isReachedTarget() {
        return this.reachedTarget;
    }

    /**
     * Searches and sets new destination block and returns true if a suitable block (specified in {@link
     * #isValidTarget(net.minecraft.world.level.LevelReader, net.minecraft.core.BlockPos)}) can be found.
     */
    protected boolean findNearestBlock() {
        int i = this.searchRange;
        int j = this.verticalSearchRange;
        BlockPos blockpos = this.mob.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int k = this.verticalSearchStart; k <= j; k = k > 0 ? -k : 1 - k) {
            for(int l = 0; l < i; ++l) {
                for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, i1, k - 1, j1);
                        if (this.mob.isWithinRestriction(blockpos$mutableblockpos) && this.isValidTarget(this.mob.level(), blockpos$mutableblockpos)) {
                            this.blockPos = blockpos$mutableblockpos;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Return {@code true} to set given position as destination
     */
    protected abstract boolean isValidTarget(LevelReader pLevel, BlockPos pPos);
}

