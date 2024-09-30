package net.reaper.ancientnature.core.init;

import net.reaper.ancientnature.AncientNature;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AncientNature.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ANCIENTNATURE_TAB = CREATIVE_MODE_TABS.register("ancientnature_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MOSQUITO_AMBER.get()))
                    .title(Component.translatable(createTranslationKey("ancientnature_tab")))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.REVIVAL_STAND.get());
                        pOutput.accept(ModItems.AMBER.get());
                        pOutput.accept(ModItems.MOSQUITO_AMBER.get());
                        pOutput.accept(ModItems.LIZARD_AMBER.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_AMBER.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_DEVONIAN_FOSSIL.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_CARBONIFEROUS.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_PERMIAN_FOSSIL.get());
                        pOutput.accept(ModBlocks.STONE_PERMIAN_FOSSIL.get());
                        pOutput.accept(ModBlocks.MUD_WITH_FOSSILS.get());
                        pOutput.accept(ModBlocks.CRETACEOUS_FOSSILS.get());
                        pOutput.accept(ModBlocks.QUATERNARY_FOSSILS.get());
                        pOutput.accept(ModBlocks.STONE_AMBER.get());
                        pOutput.accept(ModBlocks.WORM_DIRT.get());
                        pOutput.accept(ModItems.CAMBRIAN_FOSSIL.get());
                        pOutput.accept(ModItems.DEVONIAN_FOSSIL.get());
                        pOutput.accept(ModItems.CARBONIFEROUS_FOSSIL.get());
                        pOutput.accept(ModItems.DEEPSLATE_PERMIAN_FOSSIL.get());
                        pOutput.accept(ModItems.STONE_PERMIAN_FOSSIL.get());
                        pOutput.accept(ModItems.MUDDY_PERMIAN_FOSSIL.get());
                        pOutput.accept(ModItems.CRETACEOUS_FOSSIL.get());
                        pOutput.accept(ModItems.QUATERNARY_FOSSIL.get());
                        pOutput.accept(ModItems.ANOMALOCARIS_FOSSIL.get());
                        pOutput.accept(ModItems.ARANDASPIS_FOSSIL.get());
                        pOutput.accept(ModItems.DODO_FOSSIL.get());
                        pOutput.accept(ModItems.LYTHRONAX_FOSSIL.get());
                        pOutput.accept(ModItems.PARANOGMIUS_FOSSIL.get());
                        pOutput.accept(ModItems.ANOMALOCARIS_BUCKET.get());
                        pOutput.accept(ModItems.ARANDASPIS_BUCKET.get());
                        pOutput.accept(ModItems.WHERE_YOUR_JOURNEY_BEGINS_MUSIC_DISC.get());
                        pOutput.accept(ModItems.BLOOD_DAGGER.get());
                        pOutput.accept(ModItems.ROPE.get());
                        pOutput.accept(ModItems.LYTHRONAX_TEETH.get());
                        pOutput.accept(ModItems.RAW_DODO.get());
                        pOutput.accept(ModItems.RAW_PARANOGMIUS.get());
                        pOutput.accept(ModItems.WORM.get());
                        pOutput.accept(ModItems.COOKED_DODO.get());
                        pOutput.accept(ModItems.COOKED_PARANOGMIUS.get());
                        pOutput.accept(ModItems.FISH_ROE.get());
                        pOutput.accept(ModBlocks.ARANDASPIS_ROE.get());
                        pOutput.accept(ModItems.PARANOGMIUS_ROE.get());
                        pOutput.accept(ModBlocks.ANOMALOCARIS_EGGS.get());
                        pOutput.accept(ModItems.ARANDASPIS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.ANOMALOCARIS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.DODO_SPAWN_EGG.get());
                        pOutput.accept(ModItems.LYTHRONAX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.THYLACINE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.TUATARA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.TREX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.HORSESHOE_CRAB_SPAWN_EGG.get());
                        pOutput.accept(ModItems.PARANOGMIUS_SPAWN_EGG.get());
//                        pOutput.accept(ModItems.WORM_SPAWN_EGG.get());

                        pOutput.accept(ModBlocks.DODO_EGG.get());
                    })

                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    /**
     * this creates the translation key for the creative tabs
     * for example: input: "test_tab" -> "creativetab.(modid).test_tab"
     * this is used so the LanguageProvider can actually use this as translation key
     */
    public static String createTranslationKey(String name) {
        return "creativetab." + AncientNature.MOD_ID + "." + name;
    }

}
