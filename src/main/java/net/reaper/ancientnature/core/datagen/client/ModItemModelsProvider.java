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
        simple(ModItems.LIZARD_AMBER.get());
    }

    protected void simple(ItemLike... items){
        for (ItemLike item : items){
            this.basicItem(item.asItem());
        }
    }
}
