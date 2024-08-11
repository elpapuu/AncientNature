package net.reaper.ancientnature.common.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.reaper.ancientnature.AncientNature;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DEEPSLATE_AMBER_ORE_PLACED_KEY = registerKey("deepslate_amber_ore_placed");
    public static final ResourceKey<PlacedFeature> CAMBRIAN_FOSSILS_PLACED_KEY = registerKey("cambrian_fossils_placed");
    public static final ResourceKey<PlacedFeature> DEVONIAN_FOSSILS_PLACED_KEY = registerKey("devonian_fossils_placed");
    public static final ResourceKey<PlacedFeature> CARBONIFEROUS_FOSSILS_PLACED_KEY = registerKey("carboniferous_fossils_placed");
    public static final ResourceKey<PlacedFeature> PERMIAN_FOSSILS_PLACED_KEY = registerKey("permian_fossils_placed");
    public static final ResourceKey<PlacedFeature> CRETACEOUS_FOSSILS_PLACED_KEY = registerKey("cretaceous_fossils_placed");
    public static final ResourceKey<PlacedFeature> QUATERNARY_FOSSILS_PLACED_KEY = registerKey("quaternary_fossils_placed");
    public static final ResourceKey<PlacedFeature> STONE_AMBER_ORE_PLACED_KEY = registerKey("stone_amber_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CRETACEOUS_FOSSILS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_CRETACEOUS_FOSSILS_KEY),
                ModOrePlacement.commonOrePlacement(7,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(19), VerticalAnchor.absolute(31))));

        register(context, DEEPSLATE_AMBER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_DEEPSLATE_AMBER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-49))));

        register(context, STONE_AMBER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_STONE_AMBER_ORE_KEY),
                ModOrePlacement.rareOrePlacement(7,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(90), VerticalAnchor.absolute(150))));

        register(context, CAMBRIAN_FOSSILS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_CAMBRIAN_FOSSILS_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-49))));

        register(context, DEVONIAN_FOSSILS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_DEVONIAN_FOSSILS_KEY),
                ModOrePlacement.commonOrePlacement(5,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-31), VerticalAnchor.absolute(-19))));

        register(context, CARBONIFEROUS_FOSSILS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_CARBONIFEROUS_FOSSILS_KEY),
                ModOrePlacement.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-21), VerticalAnchor.absolute(-9))));

        register(context, PERMIAN_FOSSILS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_PERMIAN_FOSSILS_KEY),
                ModOrePlacement.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-11), VerticalAnchor.absolute(1))));

        register(context, QUATERNARY_FOSSILS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_QUATERNARY_FOSSILS_KEY),
                ModOrePlacement.commonOrePlacement(9,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(51), VerticalAnchor.absolute(64))));

    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(AncientNature.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}