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

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        BrushRecipeBuilder.builder(ModItems.ANOMALOCARIS_FOSSIL.get()).probability(0.95f).input(ModItems.CAMBRIAN_FOSSIL.get()).build(pWriter);
        BrushRecipeBuilder.builder(ModItems.ARANDASPIS_FOSSIL.get()).probability(0.95f).input(ModItems.DEVONIAN_FOSSIL.get()).build(pWriter);
        WaterWashingBuilder.builder(ModItems.STONE_PERMIAN_FOSSIL.get()).probability(.16f).input(ModItems.MUDDY_PERIMAN_FOSSIL.get()).build(pWriter);
        RevivalStandRecipeBuilder.builder(ModBlocks.ANOMALOCARIS_EGGS.get(), 3).fossil(5, ModItems.ANOMALOCARIS_FOSSIL.get()).amber(ModTags.Items.ANIMAL_AMBERS).amberInfusionTime(230).fossilInfusionTime(350).baseRoe(ModItems.FISH_ROE.get()).build(pWriter);
        RevivalStandRecipeBuilder.builder(ModBlocks.ARANDASPIS_ROE.get(), 3).fossil(3, ModItems.ARANDASPIS_FOSSIL.get()).amber(ModTags.Items.ANIMAL_AMBERS).amberInfusionTime(100).fossilInfusionTime(100).baseRoe(ModItems.FISH_ROE.get()).build(pWriter);
    }
}
