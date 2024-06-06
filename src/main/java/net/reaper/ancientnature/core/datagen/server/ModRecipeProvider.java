package net.reaper.ancientnature.core.datagen.server;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.reaper.ancientnature.core.datagen.server.recipe.BrushRecipeBuilder;
import net.reaper.ancientnature.core.datagen.server.recipe.RevivalStandRecipeBuilder;
import net.reaper.ancientnature.core.datagen.server.recipe.WaterWashingBuilder;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModTags;

import java.io.PrintWriter;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        BrushRecipeBuilder.builder(ModItems.ANOMALOCARIS_FOSSIL.get()).probability(0.95f).input(ModItems.CAMBRIAN_FOSSIL.get()).build(pWriter);
        WaterWashingBuilder.builder(ModItems.CAMBRIAN_FOSSIL.get()).probability(.16f).input(ModItems.MUDDY_PERIMAN_FOSSIL.get()).build(pWriter);
        RevivalStandRecipeBuilder.builder(ModBlocks.ARANDASPIS_ROE.get(), 3).fossil(4, ModItems.ARANDASPIS_FOSSIL.get()).amber(ModTags.Items.AMBER).amberInfusionTime(100).fossilInfusionTime(100).baseRoe(ModItems.FISH_ROE.get()).build(pWriter);
    }
}
