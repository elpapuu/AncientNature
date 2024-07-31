package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.ground.AbstractDinoAnimal;

import java.util.EnumSet;
import java.util.List;

public class ProtectEggGoal extends Goal {
    private final AbstractDinoAnimal mob;
    private final double speedModifier;
    private final double eggPosX, eggPosY, eggPosZ;
    private LivingEntity target;

    public ProtectEggGoal(AbstractDinoAnimal mob, double speedModifier, double eggPosX, double eggPosY, double eggPosZ) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.eggPosX = eggPosX;
        this.eggPosY = eggPosY;
        this.eggPosZ = eggPosZ;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Check for nearby entities
        List<Entity> entities = this.mob.level().getEntities(this.mob, new AABB(
                eggPosX - 10, eggPosY - 10, eggPosZ - 10,
                eggPosX + 10, eggPosY + 10, eggPosZ + 10));

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != this.mob) {
                this.target = (LivingEntity) entity;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && this.target.isAlive() && this.mob.distanceToSqr(this.target) < 100.0D;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.target, this.speedModifier);
    }

    @Override
    public void stop() {
        this.target = null;
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.target != null) {
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            if (this.mob.distanceToSqr(this.target) < 2.0D) {
                this.mob.doHurtTarget(this.target);
            } else {
                this.mob.getNavigation().moveTo(this.target, this.speedModifier);
            }
        }
    }
}
