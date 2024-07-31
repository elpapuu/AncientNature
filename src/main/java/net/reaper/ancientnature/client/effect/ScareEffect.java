package net.reaper.ancientnature.client.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class ScareEffect extends MobEffect
{
    public ScareEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Player) {
            Player player = (Player) pLivingEntity;
            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

            if (movementSpeed != null) {
                // Reduces movement speed by 20% per amplifier level
                double reductionAmount = 0.2 * (pAmplifier + 1);
                movementSpeed.setBaseValue(movementSpeed.getBaseValue() * (1 - reductionAmount));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
