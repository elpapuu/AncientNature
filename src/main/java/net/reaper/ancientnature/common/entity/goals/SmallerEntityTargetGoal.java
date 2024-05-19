package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

import java.util.function.Predicate;

public class SmallerEntityTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public SmallerEntityTargetGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee, Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, pTargetType, pMustSee, pTargetPredicate);
    }

    public boolean canUse() {
        if (this.mob.getRandom().nextBoolean()) {

            this.findTarget();
            return target != null && target.getBoundingBox().getSize() < this.mob.getBoundingBox().getSize();
        } else {

            return false;
        }
    }
}
