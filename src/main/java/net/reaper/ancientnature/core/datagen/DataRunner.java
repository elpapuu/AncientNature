package net.reaper.ancientnature.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.datagen.client.*;
import net.reaper.ancientnature.core.datagen.server.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataRunner {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> holderLookup = event.getLookupProvider();

        gen.addProvider(event.includeClient(), new ModBlockStatesProvider(output, helper));
        ModBlockTagsProvider blockTags = gen.addProvider(event.includeServer(), new ModBlockTagsProvider(output, holderLookup, helper));
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(output, holderLookup, blockTags.contentsGetter(), helper));
        gen.addProvider(event.includeServer(), new ModLoot(output));
        gen.addProvider(event.includeClient(), new ModItemModelsProvider(output, helper));
        gen.addProvider(event.includeClient(), new ModEnglishLanguageProvider(output));
        gen.addProvider(event.includeClient(), new ModSpanishLanguageProvider(output));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(output));
        gen.addProvider(event.includeClient(), new ModParticleProvider(output, helper));
        gen.addProvider(event.includeServer(), new ModLootModifierProvider(output));
        gen.addProvider(event.includeServer(), new ModEntityTagsProvider(output, holderLookup, helper));

        try {
            gen.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
