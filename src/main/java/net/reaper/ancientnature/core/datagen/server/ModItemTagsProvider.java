package net.reaper.ancientnature.core.datagen.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, AncientNature.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.FOSSILS).add(ModItems.ANOMALOCARIS_FOSSIL.get(), ModItems.CAMBRIAN_FOSSIL.get(), ModItems.CARBONIFEROUS_FOSSIL.get(), ModItems.DEEPSLATE_PERMIAN_FOSSIL.get(), ModItems.STONE_PERMIAN_FOSSIL.get(), ModItems.DEVONIAN_FOSSIL.get());
        tag(ModTags.Items.AMBER).add(ModItems.LIZARD_AMBER.get(), ModItems.MOSQUITO_AMBER.get());
    }
}
