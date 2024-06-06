package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.phys.AABB;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;

import java.util.List;

public class HerbivorePanic extends PanicSprintingGoal{
    private boolean sleeping;
    private final int detectRadiusSize;

    public HerbivorePanic(PathfinderMob pMob, double pSpeedModifier, int detectRadiusSize) {
        super(pMob, pSpeedModifier);
        this.detectRadiusSize = detectRadiusSize;
    }


    @Override
    public boolean canUse() {
        if (mob instanceof BaseTameableDinoEntity) {
            sleeping = mob.isSleeping();
        }
        if (sleeping) {
            AABB detectRadius = new AABB(this.mob.blockPosition()).inflate(detectRadiusSize).expandTowards(0.0D, (double) this.mob.level().getHeight(), 0.0D);
               List<LivingEntity> list = this.mob.level().getEntitiesOfClass(LivingEntity.class, detectRadius);
               for (LivingEntity entity : list) {
                   if(!(entity instanceof BaseTameableDinoEntity && ((BaseTameableDinoEntity) entity).isHerbivore())) {
                       if (mob instanceof BaseTameableDinoEntity) {
                          ((BaseTameableDinoEntity) mob).setSleeping(false);
                          ((BaseTameableDinoEntity) mob).setSleepTimer(1300);
                       }
                       return true;
                   }
               }
        }
        if (!this.shouldPanic()) {
            return false;
        } else {
            if (this.mob.isOnFire()) {
                BlockPos blockpos = this.lookForWater(this.mob.level(), this.mob, 5);
                if (blockpos != null) {
                    this.posX = (double)blockpos.getX();
                    this.posY = (double)blockpos.getY();
                    this.posZ = (double)blockpos.getZ();
                    return true;
                }
            }

            return this.findRandomPosition();
        }
    }
}
