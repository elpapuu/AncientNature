package net.reaper.ancientnature.core.datagen.server;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.reaper.ancientnature.core.datagen.server.recipe.BrushRecipeBuilder;
import net.reaper.ancientnature.core.init.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        BrushRecipeBuilder.builder(ModItems.ANOMALOCARIS_FOSSIL.get()).probability(0.14f).input(ModItems.CAMBRIAN_FOSSIL.get()).build(pWriter);
    }
}
