package net.reaper.ancientnature.core.compat.jei.revival;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.recipe.RevivalStandRecipe;
import net.reaper.ancientnature.core.init.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class RevivalCategory implements IRecipeCategory<RevivalStandRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public static final RecipeType<RevivalStandRecipe> RECIPE_TYPE = RecipeType.create(AncientNature.MOD_ID,"revival_stand_recipe", RevivalStandRecipe.class);

    public RevivalCategory(IGuiHelper guiHelper) {
        ResourceLocation location=AncientNature.modLoc("textures/gui/revivalstand_gui.png");
        background = guiHelper.createDrawable(location, 6, 6, 120, 75);
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.REVIVAL_STAND.get()));
        localizedName = Component.translatable("jei.category.revival_stand_recipe");
    }


    @Override
    public RecipeType<RevivalStandRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }



    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RevivalStandRecipe revivalStandRecipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.OUTPUT,100,10)
                .addItemStack(revivalStandRecipe.getResultItem(null));
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 12)
                .addIngredients(revivalStandRecipe.getAmber().toIngredientWithCounts());
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 45)
                .addItemStack(new ItemStack(Items.BLAZE_POWDER));
        builder.addSlot(RecipeIngredientRole.INPUT, 74, 10)
                .addIngredients(revivalStandRecipe.getFossil().toIngredientWithCounts());
        builder.addSlot(RecipeIngredientRole.INPUT, 53, 45)
                .addIngredients(revivalStandRecipe.getBaseRoe());
        builder.addSlot(RecipeIngredientRole.INPUT, 75, 53)
                .addIngredients(revivalStandRecipe.getBaseRoe());
        builder.addSlot(RecipeIngredientRole.INPUT, 97, 45)
                .addIngredients(revivalStandRecipe.getBaseRoe());
    }


}
