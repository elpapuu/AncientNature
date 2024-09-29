package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.reaper.ancientnature.common.entity.ground.TRexEntity;

import java.util.Objects;

public class ANMeleeGoalAttack extends MeleeAttackGoal {
    private final TRexEntity animal;
    private final int attackDelay;
    private final int animationLength;
    private int ticksUntilNextAttack;
    private boolean shouldCountTillNextAttack = false;

    public ANMeleeGoalAttack(TRexEntity pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen,int attackDelay, int attackAnimationLength) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        animal = pMob;
        this.attackDelay = attackDelay;
        this.animationLength = attackAnimationLength;
        this.ticksUntilNextAttack = attackAnimationLength-attackDelay;
    }


    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()) {
                animal.setAttacking(true);
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            animal.setAttacking(false);
            animal.attackAnimationTimeout = 0;
        }
    }



    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(animationLength);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }


    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public boolean canUse() {
        return super.canUse()&&animal.canAttack(Objects.requireNonNull(animal.getTarget()));
    }

    @Override
    public void stop() {
        animal.setAttacking(false);
        super.stop();
    }
}
