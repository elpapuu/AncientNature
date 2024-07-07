package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModItems;
import org.jetbrains.annotations.NotNull;

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
        simple(ModItems.ANOMALOCARIS_BUCKET.get());
        simple(ModItems.CARBONIFEROUS_FOSSIL.get(), ModItems.DEEPSLATE_PERMIAN_FOSSIL.get());
        simple(ModItems.MUDDY_PERMIAN_FOSSIL.get(), ModItems.STONE_PERMIAN_FOSSIL.get());
        simple(ModItems.DEVONIAN_FOSSIL.get(), ModItems.ARANDASPIS_FOSSIL.get());
        simple(ModItems.CRETACEOUS_FOSSIL.get());
        simple(ModItems.FISH_ROE.get());
        simple(ModItems.LYTHRONAX_FOSSIL.get());
        simple(ModItems.PARANOGMIUS_FOSSIL.get());
        simple(ModBlocks.ARANDASPIS_ROE.get());
        simple(ModBlocks.ANOMALOCARIS_EGGS.get());
        spawnEgg(ModItems.ARANDASPIS_SPAWN_EGG.get());
        spawnEgg(ModItems.ANOMALOCARIS_SPAWN_EGG.get());
        spawnEgg(ModItems.DODO_SPAWN_EGG.get());
        spawnEgg(ModItems.PARANOGMIUS_SPAWN_EGG.get());
        handheldItem(ModItems.BLOOD_DAGGER);
        simple(ModItems.LYTHRONAX_TEETH.get());
        simple(ModItems.ROPE.get());
        simple(ModItems.RAW_DODO.get());
        simple(ModItems.COOKED_DODO.get());
        simple(ModItems.WHERE_YOUR_JOURNEY_BEGINS_MUSIC_DISC.get());
    }

   private ItemModelBuilder handheldItem(RegistryObject<Item>item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(AncientNature.MOD_ID,"item/" + item.getId().getPath()));
    }

    protected void simple(ItemLike... items){
        for (ItemLike item : items) {
            this.basicItem(item.asItem());
        }
    }

    protected void spawnEgg(ItemLike... items){
         for (ItemLike item : items){
             ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.asItem());
             getBuilder(id.toString()).parent(getExistingFile(mcLoc("item/template_spawn_egg")));
        }
    }
}
