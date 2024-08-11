package net.reaper.ancientnature.core.init;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties RAW_DODO = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).meat().effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200), 0.1f).build();
    public static final FoodProperties COOKED_DODO = new FoodProperties.Builder().nutrition(3).saturationMod(0.7f).build();
    public static final FoodProperties RAW_PARANOGMIUS = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).meat().build();
    public static final FoodProperties COOKED_PARANOGMIUS = new FoodProperties.Builder().nutrition(7).saturationMod(0.7f).build();

}