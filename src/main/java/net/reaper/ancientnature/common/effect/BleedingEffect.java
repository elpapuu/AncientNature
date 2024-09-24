package net.reaper.ancientnature.common.effect;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.core.init.ModEffects;

public class  BleedingEffect extends MobEffect {
    public BleedingEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {

            int damage = (pAmplifier + 1);
            pLivingEntity.hurt(pLivingEntity.damageSources().magic(), damage);
        }

        if (pAmplifier == 2) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
        }
        else if (pAmplifier >= 3) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 1));
        }


        }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        if (this == ModEffects.BLEEDING.get()) {
            return pDuration % 40 == 0;
        }
        return false;
    }
}