package net.reaper.ancientnature.common.item;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.item.*;

import net.reaper.ancientnature.core.init.ModEffects;



public class BloodDaggerItem extends SwordItem {


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 280, 1));
        return super.hurtEnemy(stack, target, attacker);
    }
    public BloodDaggerItem(Properties properties) {
        super(Tiers.STONE, 2, 10.0F, new Item.Properties().rarity(Rarity.UNCOMMON));
    }

}








