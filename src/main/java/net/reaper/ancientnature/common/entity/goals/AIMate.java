package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;

import java.util.EnumSet;
import java.util.List;

public class AIMate extends Goal {
    private final AbstractTrexEntity dragon;
    Level theWorld;
    int spawnBabyDelay;
    double moveSpeed;
    private AbstractTrexEntity targetMate;

    public AIMate(AbstractTrexEntity dragon, double speedIn) {
        this.dragon = dragon;
        this.theWorld = dragon.level();
        this.moveSpeed = speedIn;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.dragon.isInLove() || !this.dragon.canMove()) {
            return false;
        } else {
            this.targetMate = this.getNearbyMate();
            return this.targetMate != null;
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    public boolean continueExecuting() {
        return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        this.dragon.getLookControl().setLookAt(this.targetMate, 10.0F, this.dragon.getMaxHeadXRot());
        this.dragon.getNavigation().moveTo(targetMate.getX(), targetMate.getY(), targetMate.getZ(), this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay >= 60 && this.dragon.distanceTo(this.targetMate) < 35) {
            this.spawnBaby();
        }
    }

    /**
     * Loops through nearby animals and finds another animal of the same type that can be mated with. Returns the first
     * valid mate found.
     */
    private AbstractTrexEntity getNearbyMate() {
        List<? extends AbstractTrexEntity> list = this.theWorld.getEntitiesOfClass(this.dragon.getClass(), this.dragon.getBoundingBox().inflate(180.0D, 180.0D, 180.0D));
        double d0 = Double.MAX_VALUE;
        AbstractTrexEntity mate = null;
        for (AbstractTrexEntity partner : list) {
            if (this.dragon.canMate(partner)) {
                double d1 = this.dragon.distanceToSqr(partner);
                if (d1 < d0) { // find min distance
                    mate = partner;
                    d0 = d1;
                }
            }
        }
        return mate;
    }

    /**
     * Spawns a baby animal of the same type.
     */
    private void spawnBaby() {

//        AbstractDinoEgg egg = this.dragon.createEgg(this.targetMate);
//
//        if (egg != null) {
////            PlayerEntity PlayerEntity = this.dragon.getLoveCause();
////
////            if (PlayerEntity == null && this.targetMate.getLoveCause() != null) {
////                PlayerEntity = this.targetMate.getLoveCause();
////            }
//
//            this.dragon.setAge(6000);
//            this.targetMate.setAge(6000);
//            this.dragon.resetLove();
//            this.targetMate.resetLove();
//            int nestX = (int) (this.dragon.getGender() ? this.targetMate.getX() : this.dragon.getX());
//            int nestY = (int) (this.dragon.getGender() ? this.targetMate.getY() : this.dragon.getY()) - 1;
//            int nestZ = (int) (this.dragon.getGender() ? this.targetMate.getZ() : this.dragon.getZ());
//
//            egg.moveTo(nestX - 0.5F, nestY + 1F, nestZ - 0.5F, 0.0F, 0.0F);
//            this.theWorld.addFreshEntity(egg);
//            RandomSource random = this.dragon.getRandom();
//
//            for (int i = 0; i < 17; ++i) {
//                final double d0 = random.nextGaussian() * 0.02D;
//                final double d1 = random.nextGaussian() * 0.02D;
//                final double d2 = random.nextGaussian() * 0.02D;
//                final double d3 = random.nextDouble() * this.dragon.getBbWidth() * 2.0D - this.dragon.getBbWidth();
//                final double d4 = 0.5D + random.nextDouble() * this.dragon.getBbHeight();
//                final double d5 = random.nextDouble() * this.dragon.getBbWidth() * 2.0D - this.dragon.getBbWidth();
//                this.theWorld.addParticle(ParticleTypes.HEART, this.dragon.getX() + d3, this.dragon.getY() + d4, this.dragon.getZ() + d5, d0, d1, d2);
//            }
//            BlockPos eggPos = new BlockPos(nestX - 2, nestY, nestZ - 2);
//            BlockPos dirtPos = eggPos.offset(1, 0, 1);
//
//            for (int x = 0; x < 3; x++) {
//                for (int z = 0; z < 3; z++) {
//                    BlockPos add = eggPos.offset(x, 0, z);
//                    BlockState prevState = theWorld.getBlockState(add);
//                    if (prevState.canBeReplaced() || theWorld.getBlockState(add).is(BlockTags.DIRT) || theWorld.getBlockState(add).getDestroySpeed(theWorld, add) < 5F || theWorld.getBlockState(add).getDestroySpeed(theWorld, add) >= 0F) {
//
//                    }
//                }
//            }
//            if (theWorld.getBlockState(dirtPos).canBeReplaced()) {
//                theWorld.setBlockAndUpdate(dirtPos, Blocks.DIRT_PATH.defaultBlockState());
//            }
//            if (this.theWorld.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
//                this.theWorld.addFreshEntity(new ExperienceOrb(this.theWorld, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), random.nextInt(15) + 10));
//            }
//        }
    }
}
