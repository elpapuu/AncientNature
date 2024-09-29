package net.reaper.ancientnature.common.entity.goals;

import com.google.common.base.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;
import net.reaper.ancientnature.common.util.FoodUtils;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class AITargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> 
{
    private final AbstractTrexEntity dino;

    public AITargetGoal(AbstractTrexEntity entityIn, Class<T> classTarget, boolean checkSight, Predicate<LivingEntity> targetSelector) {
        super(entityIn, classTarget, 3, checkSight, false, targetSelector);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.dino = entityIn;
    }

    @Override
    public boolean canUse() {
        if (dino.isSleeping()) {
            return false;
        }
        if (!dino.isTame()) {
            return false;
        }
        if (target != null && !target.getClass().equals(this.dino.getClass())) {
            if (!super.canUse())
                return false;

            final float dinoSize = Math.max(this.dino.getBbWidth(), this.dino.getBbWidth());
            if (dinoSize >= target.getBbWidth()) {
                if (target instanceof Player && !dino.isTame()) {
                    return true;
                }
                if (target instanceof AbstractTrexEntity) {
                    AbstractTrexEntity dino = (AbstractTrexEntity) target;
                    if (dino.getOwner() != null && this.dino.getOwner() != null && this.dino.isOwnedBy(dino.getOwner())) {
                        return false;
                    }
                    return !dino.isDeadOrDying();
                }
                if (target instanceof Player && dino.isTame()) {
                    return false;
                } else {
                    if (!dino.isOwnedBy(target) && FoodUtils.getFoodPoints(target) > 0 && dino.canMove() && (!dino.isTame() && target instanceof Player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.dino.getTarget() != null)
        {
            this.dino.setSprinting(true);
        }
    }

    @Override
    protected @NotNull AABB getTargetSearchArea(double targetDistance) {
        return this.dino.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
    }

    @Override
    protected double getFollowDistance() {
        AttributeInstance iattributeinstance = this.mob.getAttribute(Attributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 64.0D : iattributeinstance.getValue();
    }
}
