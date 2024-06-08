package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.water.Paranogmius;

public class ParanogmiusJumpGoal extends JumpGoal {
    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
    private Paranogmius paranogmius;
    private final int interval;
    private boolean breached;

    public ParanogmiusJumpGoal(Paranogmius paranogmius, int pInterval) {
        this.paranogmius = paranogmius;
        this.interval = reducedTickDelay(pInterval);
    }

    public boolean canUse() {
        if (this.paranogmius.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction $$0 = this.paranogmius.getMotionDirection();
            int $$1 = $$0.getStepX();
            int $$2 = $$0.getStepZ();
            BlockPos $$3 = this.paranogmius.blockPosition();
            int[] var5 = STEPS_TO_CHECK;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                int $$4 = var5[var7];
                if (!this.waterIsClear($$3, $$1, $$2, $$4) || !this.surfaceIsClear($$3, $$1, $$2, $$4)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean waterIsClear(BlockPos pPos, int pDx, int pDz, int pScale) {
        BlockPos $$4 = pPos.offset(pDx * pScale, 0, pDz * pScale);
        return this.paranogmius.level().getFluidState($$4).is(FluidTags.WATER) && !this.paranogmius.level().getBlockState($$4).blocksMotion();
    }

    private boolean surfaceIsClear(BlockPos pPos, int pDx, int pDz, int pScale) {
        return this.paranogmius.level().getBlockState(pPos.offset(pDx * pScale, 1, pDz * pScale)).isAir() && this.paranogmius.level().getBlockState(pPos.offset(pDx * pScale, 3, pDz * pScale)).isAir();
    }

    public boolean canContinueToUse() {
        double $$0 = this.paranogmius.getDeltaMovement().y;
        return (!($$0 * $$0 < 0.029999999329447746) || this.paranogmius.getXRot() == 0.0F || !(Math.abs(this.paranogmius.getXRot()) < 10.0F) || !this.paranogmius.isInWater()) && !this.paranogmius.onGround();
    }

    public boolean isInterruptable() {
        return false;
    }

    public void start() {
        Direction $$0 = this.paranogmius.getMotionDirection();
        this.paranogmius.setDeltaMovement(this.paranogmius.getDeltaMovement().add((double)$$0.getStepX() * 0.6, 0.7, (double)$$0.getStepZ() * 0.6));
        this.paranogmius.getNavigation().stop();
    }

    public void stop() {
        this.paranogmius.setXRot(0.0F);
    }

    public void tick() {
        boolean $$0 = this.breached;
        if (!$$0) {
            FluidState $$1 = this.paranogmius.level().getFluidState(this.paranogmius.blockPosition());
            this.breached = $$1.is(FluidTags.WATER);
        }

        if (this.breached && !$$0) {
            this.paranogmius.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 $$2 = this.paranogmius.getDeltaMovement();
        if ($$2.y * $$2.y < 0.029999999329447746 && this.paranogmius.getXRot() != 0.0F) {
            this.paranogmius.setXRot(Mth.rotLerp(0.2F, this.paranogmius.getXRot(), 0.0F));
        } else if ($$2.length() > 9.999999747378752E-6) {
            double $$3 = $$2.horizontalDistance();
            double $$4 = Math.atan2(-$$2.y, $$3) * 57.2957763671875;
            this.paranogmius.setXRot((float)$$4);
        }

    }
}
