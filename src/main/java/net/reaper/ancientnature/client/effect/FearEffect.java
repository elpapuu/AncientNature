package net.reaper.ancientnature.client.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FearEffect extends MobEffect
{

    public FearEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        if (!(pLivingEntity instanceof Player)) {
            if (pLivingEntity.hasEffect(this)) {
                for (Player target : pLivingEntity.level().players())
                {
                    if (target != null) {
                        Vec3 playerPos = target.position();
                        Vec3 entityPos = pLivingEntity.position();
                        Vec3 fleeDirection = entityPos.subtract(playerPos).normalize().scale(1.5);

                        pLivingEntity.setDeltaMovement(fleeDirection);

                        AttributeInstance movementSpeedAttribute = pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                        if (movementSpeedAttribute != null) {
                            double baseSpeed = movementSpeedAttribute.getBaseValue();
                            movementSpeedAttribute.setBaseValue(baseSpeed + (0.1 * (pAmplifier + 1)));
                        }
                    }
                }

            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }
}
