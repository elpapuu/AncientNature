package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModItems;

public class ModItemModelsProvider extends ItemModelProvider {

    protected ModelFile.ExistingModelFile generated = getExistingFile(mcLoc("item/generated"));
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AncientNature.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simple(ModItems.LIZARD_AMBER.get(), ModItems.AMBER.get());
        simple(ModItems.ANOMALOCARIS_FOSSIL.get(), ModItems.CAMBRIAN_FOSSIL.get());
        simple(ModItems.MOSQUITO_AMBER.get());
        simple(ModItems.ARANDASPIS_BUCKET.get());
        simple(ModItems.CARBONIFEROUS_FOSSIL.get(), ModItems.DEEPSLATE_PERMIAN_FOSSIL.get());
        simple(ModItems.MUDDY_PERIMAN_FOSSIL.get(), ModItems.STONE_PERMIAN_FOSSIL.get());

    }

    protected void simple(ItemLike... items){
        for (ItemLike item : items){
            this.basicItem(item.asItem());
        }
    }
}
