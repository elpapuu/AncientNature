package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;
import net.reaper.ancientnature.common.entity.ground.OviraptorEntity;

import javax.annotation.Nullable;

public class OrderRandomStrollAvoidWater extends RandomStrollGoal {
    public static final float PROBABILITY = 0.001F;
    protected final float probability;
    private int number;
    private LivingEntity owner;
    private int timeToRecalcPath;

    public OrderRandomStrollAvoidWater(TamableAnimal pMob, double pSpeedModifier, int order) {
        this(pMob, pSpeedModifier, 0.001F, order);

    }

    public OrderRandomStrollAvoidWater(TamableAnimal pMob, double pSpeedModifier, float pProbability, int order) {
        super(pMob, pSpeedModifier);
        this.probability = pProbability;
        this.number = order;
        owner = pMob.getOwner();
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
            return vec3 == null ? super.getPosition() : vec3;
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
        }
    }



@Override
    public void start() {
        this.timeToRecalcPath = 0;
        if (number == 3) {
            super.start();
        }
    }

    public boolean canUse() {
        if(mob instanceof BaseTameableDinoEntity) {
          this.number = ((BaseTameableDinoEntity) mob).getOrder();
          this.owner = ((BaseTameableDinoEntity) mob).getOwner();
        }
        if(number == 3) {
          //  System.out.println("the order for Stroll is: " + number + " returns true");
            return super.canUse();
       }
       else if (number == 2) {
         //   System.out.println("the order for Following is: " + number + " returns true");
            if (owner == null) {
                return false;
            } else if (owner.isSpectator()) {
                return false;
            } else if (this.mob.distanceToSqr(owner) < (double)(100)) {
                return false;
            } else {
                this.owner = owner;
                return true;
            }
        }
        else {
           // System.out.println("the order for Stroll is: " + number + " returns false");
            return false;
        }
    }

    public void tick() {
        if(number == 2) {
            this.mob.getLookControl().setLookAt(this.owner, 10.0F, (float) this.mob.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (this.mob.distanceToSqr(this.owner) >= 144.0D) {
                    this.teleportToOwner();
                } else {
                    this.mob.getNavigation().moveTo(this.owner, this.speedModifier);
                }

            }
        }
    }




    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();
        this.mob.moveTo(blockpos.getX(), blockpos.getY(), blockpos.getZ());



    }


    private int randomIntInclusive(int pMin, int pMax) {
        return this.mob.getRandom().nextInt(pMax - pMin + 1) + pMin;
    }
}