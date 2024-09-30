package net.reaper.ancientnature.common.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DEEPSLATE_AMBER_ORE_KEY = registerKey("deepslate_amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_CAMBRIAN_FOSSILS_KEY = registerKey("cambrian_fossils");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DEVONIAN_FOSSILS_KEY = registerKey("devonian_fossils");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_CARBONIFEROUS_FOSSILS_KEY = registerKey("carboniferous_fossils");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_PERMIAN_FOSSILS_KEY = registerKey("permian_fossils");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_CRETACEOUS_FOSSILS_KEY = registerKey("cretaceous_fossils");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_QUATERNARY_FOSSILS_KEY = registerKey("quaternary_fossils");
    static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_STONE_AMBER_ORE_KEY = registerKey("stone_amber_ore");
    static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_WORM_DIRT_KEY = registerKey("worm_dirt");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest dirtReplaceables = new TagMatchTest(BlockTags.DIRT);

        List<OreConfiguration.TargetBlockState> overworldCretaceousFossilsOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.CRETACEOUS_FOSSILS.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.CRETACEOUS_FOSSILS.get().defaultBlockState()));

        register(context, OVERWORLD_CRETACEOUS_FOSSILS_KEY, Feature.ORE, new OreConfiguration(overworldCretaceousFossilsOres, 7));

        List<OreConfiguration.TargetBlockState> overworldCambrianFossilOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get().defaultBlockState()));

        register(context, OVERWORLD_CAMBRIAN_FOSSILS_KEY, Feature.ORE, new OreConfiguration(overworldCambrianFossilOres, 4));

        List<OreConfiguration.TargetBlockState> overworldDevonianFossilOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.DEEPSLATE_DEVONIAN_FOSSIL.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_DEVONIAN_FOSSIL.get().defaultBlockState()));

        register(context, OVERWORLD_DEVONIAN_FOSSILS_KEY, Feature.ORE, new OreConfiguration(overworldDevonianFossilOres, 5));

        List<OreConfiguration.TargetBlockState> overworldCarboniferousFossilOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.DEEPSLATE_CARBONIFEROUS.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_CARBONIFEROUS.get().defaultBlockState()));

        register(context, OVERWORLD_CARBONIFEROUS_FOSSILS_KEY, Feature.ORE, new OreConfiguration(overworldCarboniferousFossilOres, 5));

        List<OreConfiguration.TargetBlockState> overworldPermianFossilOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.STONE_PERMIAN_FOSSIL.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_PERMIAN_FOSSIL.get().defaultBlockState()));

        register(context, OVERWORLD_PERMIAN_FOSSILS_KEY, Feature.ORE, new OreConfiguration(overworldPermianFossilOres, 6));

        List<OreConfiguration.TargetBlockState> overworldQuaternaryFossilOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.QUATERNARY_FOSSILS.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.QUATERNARY_FOSSILS.get().defaultBlockState()));

        register(context, OVERWORLD_QUATERNARY_FOSSILS_KEY, Feature.ORE, new OreConfiguration(overworldQuaternaryFossilOres, 6));


        List<OreConfiguration.TargetBlockState> overworldDeepslateAmberOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.DEEPSLATE_AMBER.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_AMBER.get().defaultBlockState()));

        register(context, OVERWORLD_DEEPSLATE_AMBER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldDeepslateAmberOres, 3));

        List<OreConfiguration.TargetBlockState> overworldStoneAmberOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.STONE_AMBER.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.STONE_AMBER.get().defaultBlockState()));

        register(context, OVERWORLD_STONE_AMBER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldStoneAmberOres, 7));

        List<OreConfiguration.TargetBlockState> overworldWormmDirt = List.of(OreConfiguration.target(dirtReplaceables,
                        ModBlocks.WORM_DIRT.get().defaultBlockState()),
                OreConfiguration.target(stoneReplaceable, ModBlocks.WORM_DIRT.get().defaultBlockState()));

        register(context, OVERWORLD_WORM_DIRT_KEY, Feature.ORE, new OreConfiguration(overworldWormmDirt, 4));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(AncientNature.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
