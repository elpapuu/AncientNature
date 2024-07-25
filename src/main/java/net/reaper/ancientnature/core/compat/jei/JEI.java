package net.reaper.ancientnature.core.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.menu.RevivalStandMenu;
import net.reaper.ancientnature.core.compat.jei.revival.RevivalCategory;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModMenus;
import net.reaper.ancientnature.core.init.ModRecipes;


@JeiPlugin
@SuppressWarnings("unused")
public class JEI implements IModPlugin {

    private static final ResourceLocation ID = AncientNature.modLoc("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new RevivalCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RevivalCategory.RECIPE_TYPE, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipes.REVIVAL_STAND_RECIPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.REVIVAL_STAND.get()), RevivalCategory.RECIPE_TYPE);
    }

}
