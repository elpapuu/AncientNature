package net.reaper.ancientnature.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class CustomMobEffectInstance extends MobEffectInstance {

    public CustomMobEffectInstance(MobEffect pEffect, int pDuration, int pAmplifier) {
        super(pEffect, pDuration, pAmplifier);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList();
    }
}
