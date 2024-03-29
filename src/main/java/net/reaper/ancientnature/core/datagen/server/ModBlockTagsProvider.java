package net.reaper.ancientnature.core.datagen.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModBlockEntities;
import net.reaper.ancientnature.core.init.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AncientNature.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SUSPICIOUS_FOSSILIZED_GRAVEL.get(), ModBlocks.FOSSILIZED_GRAVEL.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.DEEPSLATE_AMBER.get(), ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.DEEPSLATE_AMBER.get(), ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
    }
}
