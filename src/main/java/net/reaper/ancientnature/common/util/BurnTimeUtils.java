package net.reaper.ancientnature.common.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

public class BurnTimeUtils {

    public static int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            if (recipeType == RecipeType.SMOKING || recipeType == RecipeType.BLASTING || recipeType == RecipeType.SMELTING || recipeType == RecipeType.CAMPFIRE_COOKING || recipeType == null) {
                return ForgeHooks.getBurnTime(stack, recipeType);
            }
            int ret = stack.getBurnTime(recipeType);
            return ForgeEventFactory.getItemBurnTime(stack, Math.max(0, ret), recipeType);
        }
    }
}
