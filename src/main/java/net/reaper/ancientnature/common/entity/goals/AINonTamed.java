package net.reaper.ancientnature.common.entity.goals;

import com.google.common.base.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class AINonTamed<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final AbstractTrexEntity dragon;

    public AINonTamed(AbstractTrexEntity entityIn, Class<T> classTarget, boolean checkSight, Predicate<LivingEntity> targetSelector) {
        super(entityIn, classTarget, 5, checkSight, false, targetSelector);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.dragon = entityIn;
    }

    @Override
    public boolean canUse() {
        if (!dragon.isTame()) {
            return false;
        }
        return !dragon.isTame() && !dragon.isSleeping() && super.canUse();
    }

    @Override
    protected @NotNull AABB getTargetSearchArea(double targetDistance) {
        return this.dragon.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
    }

    @Override
    protected double getFollowDistance() {
        AttributeInstance iattributeinstance = this.mob.getAttribute(Attributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 128.0D : iattributeinstance.getValue();
    }
}