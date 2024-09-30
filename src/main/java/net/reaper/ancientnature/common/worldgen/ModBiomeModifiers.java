package net.reaper.ancientnature.common.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_DEEPSLATE_AMBER_ORES = registerKey("add_deepslate_amber_ores");
    public static final ResourceKey<BiomeModifier> ADD_CAMBRIAN_FOSSILS = registerKey("add_cambrian_fossils");
    public static final ResourceKey<BiomeModifier> ADD_DEVONIAN_FOSSILS = registerKey("add_devonian_fossils");
    public static final ResourceKey<BiomeModifier> ADD_CARBONIFEROUS_FOSSILS = registerKey("add_carboniferous_fossils");
    public static final ResourceKey<BiomeModifier> ADD_PERMIAN_FOSSILS = registerKey("add_permian_fossils");
    public static final ResourceKey<BiomeModifier> ADD_CRETACEOUS_FOSSILS = registerKey("add_cretaceous_fossils");
    public static final ResourceKey<BiomeModifier> ADD_QUATERNARY_FOSSILS = registerKey("add_quaternary_fossils");
    public static final ResourceKey<BiomeModifier> ADD_STONE_AMBER_ORES = registerKey("add_stone_amber_ores");
    public static final ResourceKey<BiomeModifier> ADD_WORM_DIRT = registerKey("add_worm_dirt");


    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_CAMBRIAN_FOSSILS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CAMBRIAN_FOSSILS_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DEVONIAN_FOSSILS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DEVONIAN_FOSSILS_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_CARBONIFEROUS_FOSSILS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CARBONIFEROUS_FOSSILS_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_PERMIAN_FOSSILS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PERMIAN_FOSSILS_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_CRETACEOUS_FOSSILS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CRETACEOUS_FOSSILS_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_QUATERNARY_FOSSILS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.QUATERNARY_FOSSILS_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DEEPSLATE_AMBER_ORES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DEEPSLATE_AMBER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_STONE_AMBER_ORES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.STONE_AMBER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_WORM_DIRT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WORM_DIRT_PLACED_KEY)),
                GenerationStep.Decoration.SURFACE_STRUCTURES));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(AncientNature.MOD_ID, name));
    }
}