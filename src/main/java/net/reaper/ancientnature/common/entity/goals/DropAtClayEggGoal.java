package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class DropAtClayEggGoal extends MoveToBlockGoal {


    private final Block blockToRemove;
    private final Mob removerMob;
    private int ticksSinceReachedGoal;
    private static final int WAIT_AFTER_BLOCK_FOUND = 20;

   public DropAtClayEggGoal(Block pBlockToRemove, PathfinderMob pRemoverMob, double pSpeedModifier, int pSearchRange) {
        super(pRemoverMob, pSpeedModifier, 24, pSearchRange);
        this.blockToRemove = pBlockToRemove;
        this.removerMob = pRemoverMob;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean canUse() {
        if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.removerMob.level(), this.removerMob)) {
            return false;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else if (mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() || mob.getTarget() != null || mob.getLastHurtByMob() != null) {
        //    System.out.println("False: has either got a target, was hurt, or can move is false, or dosnt have item in hand");
            return false;
        } else if (this.findNearestBlock()) {
            this.nextStartTick = reducedTickDelay(20);
            return true;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            return false;
        }
    }




    public void stop() {
        super.stop();
        this.nextStartTick = 100;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
        this.ticksSinceReachedGoal = 0;
    }

    public void playDestroyProgressSound(LevelAccessor pLevel, BlockPos pPos) {
    }

    public void playBreakSound(Level pLevel, BlockPos pPos) {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        Level level = this.removerMob.level();
        BlockPos blockpos = this.removerMob.blockPosition();
        BlockPos blockpos1 = this.getPosWithBlock(blockpos, level);

        if (this.isReachedTarget() && blockpos1 != null) {

            if (this.ticksSinceReachedGoal > 10) {
                // Check if the entity has an item in its hand
                ItemStack itemStack = this.removerMob.getItemBySlot(EquipmentSlot.MAINHAND);
                if (!itemStack.isEmpty()) {
                    // Drop the item
                    ItemEntity itemEntity = new ItemEntity(this.removerMob.level(), blockpos1.getX() + 0.5D, blockpos1.getY() + 1D, blockpos1.getZ() + 0.5D, itemStack);
                    level.addFreshEntity(itemEntity);
                    // Remove the item from the entity's hand
                    this.removerMob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);

                }
            }

            ++this.ticksSinceReachedGoal;
        }
    }

    @Nullable
    private BlockPos getPosWithBlock(BlockPos pPos, BlockGetter pLevel) {
        if (pLevel.getBlockState(pPos).is(this.blockToRemove)) {
            return pPos;
        } else {
            BlockPos[] ablockpos = new BlockPos[]{pPos.below(), pPos.west(), pPos.east(), pPos.north(), pPos.south(), pPos.below().below()};

            for(BlockPos blockpos : ablockpos) {
                if (pLevel.getBlockState(blockpos).is(this.blockToRemove)) {
                    return blockpos;
                }
            }

            return null;
        }
    }

    /**
     * Return {@code true} to set given position as destination
     */
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        ChunkAccess chunkaccess = pLevel.getChunk(SectionPos.blockToSectionCoord(pPos.getX()), SectionPos.blockToSectionCoord(pPos.getZ()), ChunkStatus.FULL, false);
        if (chunkaccess == null || mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else {
            if (!chunkaccess.getBlockState(pPos).canEntityDestroy(pLevel, pPos, this.removerMob)) return false;
            return chunkaccess.getBlockState(pPos).is(this.blockToRemove) && chunkaccess.getBlockState(pPos.above()).isAir() && chunkaccess.getBlockState(pPos.above(2)).isAir();
        }
    }
}

