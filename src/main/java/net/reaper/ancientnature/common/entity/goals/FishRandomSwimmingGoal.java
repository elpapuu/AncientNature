package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.util.FishRandomSwimWeights;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FishRandomSwimmingGoal extends Goal {

    protected final Mob mob;
    protected final Set<Class<? extends LivingEntity>> avoidClasses = new HashSet<>();
    protected final double speedModifier;
    protected final FishRandomSwimWeights weights;

    public FishRandomSwimmingGoal(Mob mob, double speedModifier, FishRandomSwimWeights weights1) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.weights = weights1;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public FishRandomSwimmingGoal addAvoidClass(Class<? extends LivingEntity> clazz) {
        this.avoidClasses.add(clazz);
        return this;
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle())
            return false;
        return true;
    }

    @Override
    public void start() {
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        Vec3 forward = new Vec3(1, 0, 0).yRot(-mob.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
        Vec3 movement = forward.add(this.mob.getDeltaMovement().scale(this.weights.existingDirectionWeight()));
        movement = movement.add(getNoise().scale(this.weights.noiseWeight()));
        movement = movement.add(getAwayFromObstacles().scale(this.weights.avoidObstaclesWeight()));
        movement = movement.add(awayFromEntities().scale(this.weights.avoidEntitiesWeight()));

        movement = movement.normalize();
        float f9 = (float) (Mth.atan2(movement.x, movement.z) * (double) (180F / (float) Math.PI)) - 90.0F;
        this.mob.setYRot(rotlerp(this.mob.getYRot(), f9, 90.0F));
        this.mob.setSpeed((float) (mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.speedModifier));
        this.mob.setZza((float) movement.z);
        this.mob.setXxa((float) movement.x);
        this.mob.setYya((float) movement.y);

    }

    protected Vec3 getNoise() {
        double dx = this.mob.level().random.nextGaussian() * .4d - .2d;
        double dy = this.mob.level().random.nextGaussian() * .4d - .2d;
        double dz = this.mob.level().random.nextGaussian() * .4d - .2d;
        return new Vec3(dx, dy, dz);
    }


    protected Vec3 awayFromEntities() {
        if (this.avoidClasses.isEmpty())
            return Vec3.ZERO;
        Vec3 direction = Vec3.ZERO;
        List<LivingEntity> entities = this.mob.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.mob.getOnPos()).inflate(10), e -> EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e) && this.avoidClasses.contains(e.getClass()));
        for (LivingEntity livingEntity : entities) {
            Vec3 avoidEntity = new Vec3(this.mob.getX() - livingEntity.getX(), this.mob.getY() - livingEntity.getY(), this.mob.getZ() - livingEntity.getZ());
            direction.add(avoidEntity);
        }
        if (!entities.isEmpty()) {
            direction.scale(1d / entities.size());
        }

        return direction;
    }

    protected Vec3 getAwayFromObstacles() {
        AtomicReference<Vec3> directionAtomic = new AtomicReference<>(Vec3.ZERO);
        AtomicInteger counter = new AtomicInteger(0);
        BlockPos.betweenClosedStream(new AABB(this.mob.getOnPos()).inflate(10)).forEach(pos -> {
            if (!canSwim(pos)) {
                Vec3 avoiding = new Vec3(this.mob.getX() - pos.getX(), this.mob.getY() - pos.getY(), this.mob.getZ() - pos.getZ());
                directionAtomic.set(new Vec3(directionAtomic.get().x + avoiding.x, directionAtomic.get().y + avoiding.y, directionAtomic.get().z + avoiding.z));
                counter.incrementAndGet();
            }
        });
        Vec3 direction = directionAtomic.get();
        if (counter.get() > 0) {
            direction.scale((double) 1 / counter.get());
        }
        return direction;
    }

    protected boolean canSwim(BlockPos pos) {
        BlockState state = this.mob.level().getBlockState(pos);
        FluidState fluidSTate = this.mob.level().getFluidState(pos);
        if (!fluidSTate.is(FluidTags.WATER))
            return false;
        if (!state.getCollisionShape(this.mob.level(), pos).isEmpty())
            return false;
        return true;
    }

    protected static float rotlerp(float pSourceAngle, float pTargetAngle, float pMaximumChange) {
        float f = Mth.wrapDegrees(pTargetAngle - pSourceAngle);
        if (f > pMaximumChange) {
            f = pMaximumChange;
        }

        if (f < -pMaximumChange) {
            f = -pMaximumChange;
        }

        float f1 = pSourceAngle + f;
        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }

        return f1;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
