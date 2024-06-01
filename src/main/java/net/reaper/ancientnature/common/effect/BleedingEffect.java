package net.reaper.ancientnature.common.effect;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.core.init.ModEffects;

public class BleedingEffect extends MobEffect {
    public BleedingEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        if (this == ModEffects.BLEEDING.get()) {
            int j = 25 >> pAmplifier;
            if (j > 0) {
                return pDuration % j == 0;

            } else {
                return true;
            }
        }
        return false;
    }
}