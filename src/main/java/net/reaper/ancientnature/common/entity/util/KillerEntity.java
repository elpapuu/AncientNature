package net.reaper.ancientnature.common.entity.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public interface KillerEntity {

    /**
     * @return whether this entity can Hunt for prey
     */
    boolean canHunt();

    /**
     * called when an entity is found and reached
     *
     * @param prey the prey that is reached
     */
    void onAttack(LivingEntity prey);


    /**
     * performs an attack from the attacker to the prey
     */
    default void attack(LivingEntity attacker, LivingEntity prey) {
        attacker.swing(InteractionHand.MAIN_HAND);
        attacker.doHurtTarget(prey);
    }

    default double getAttackReachSqr(LivingEntity attacker, LivingEntity pAttackTarget) {
        return (double) (attacker.getBbWidth() * 2.0F * attacker.getBbWidth() * 2.0F + pAttackTarget.getBbWidth());
    }

    default int getAttackCooldown(){
        return 20;
    }
}
