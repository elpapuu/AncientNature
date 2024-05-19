package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class EnsnareGoal extends Goal {
    private final Mob entity;
    private LivingEntity target;
    private final float yd;

    public EnsnareGoal(Mob entity, float pYd) {
        this.entity = entity;
        this.yd = pYd;
    }

    @Override
    public boolean canUse() {
        if (this.entity.isVehicle()) {
            return false;
        } else {
            this.target = this.entity.getTarget();
            if (this.target == null) {
                return false;
            } else {
                double range = this.entity.distanceToSqr(this.target);
                if (!(range < 4.0D) && !(range > 16.0D)) {

                    return this.entity.getRandom().nextInt(reducedTickDelay(5)) == 0;
                } else {
                    return false;
                }
            }
        }
    }

    public void start() {
        Vec3 vector = this.entity.getDeltaMovement();
        Vec3 ray = new Vec3(this.target.getX() - this.entity.getX(), 0.0D, this.target.getZ() - this.entity.getZ());
        if (ray.lengthSqr() > 1.0E-7D) {
            ray = ray.normalize().scale(0.4D).add(vector.scale(0.2D));
        }

        this.entity.setDeltaMovement(ray.x, this.yd, ray.z);

        if (this.entity.getMeleeAttackRangeSqr(target) >= this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ())) {

            target.setXRot(this.entity.getXRot());
            target.setYRot(this.entity.getYRot());
            target.startRiding(this.entity, true);
        }
    }
}
