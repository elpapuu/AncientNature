package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.menu.UtilMenu;
import net.reaper.ancientnature.core.init.*;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, AncientNature.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        auto(ModItems.AMBER.get());
        auto(ModItems.CAMBRIAN_FOSSIL.get());
        auto(ModItems.ANOMALOCARIS_FOSSIL.get());
        auto(ModItems.LIZARD_AMBER.get());
        auto(ModItems.MOSQUITO_AMBER.get());
        auto(ModItems.ARANDASPIS_BUCKET.get());
        auto(ModItems.CARBONIFEROUS_FOSSIL.get());
        auto(ModItems.DEEPSLATE_PERMIAN_FOSSIL.get());
        auto(ModItems.MUDDY_PERIMAN_FOSSIL.get());
        auto(ModItems.STONE_PERMIAN_FOSSIL.get());
        auto(ModItems.ARANDASPIS_SPAWN_EGG.get());
        auto(ModItems.DEVONIAN_FOSSIL.get());
        auto(ModItems.ARANDASPIS_FOSSIL.get());
        auto(ModBlocks.ARANDASPIS_ROE.get());
        auto(ModItems.FISH_ROE.get());

        auto(ModBlocks.REVIVAL_STAND.get());
        auto(ModBlocks.MUD_WITH_FOSSILS.get());
        auto(ModBlocks.DEEPSLATE_AMBER.get());
        auto(ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
        auto(ModBlocks.STONE_PERMIAN_FOSSIL.get());
        auto(ModBlocks.DEEPSLATE_DEVONIAN_FOSSIL.get());
        auto(ModBlocks.DEEPSLATE_CARBONIFEROUS.get());

        auto(ModEntities.ARANDASPIS.get());
        auto(ModEntities.ANOMALOCRIS.get());

        guiTranslation(ModBlockEntities.REVIVAL_STAND.get());

        //advancements
        add("advancements.cleaning_the_past_for_the_future.title", "Belongs to a museum");
        add("advancements.cleaning_the_past_for_the_future.descr", "Clean up a fossil for see what have inside!");
        add("advancements.paleontologist.title", "Paleontologist");
        add("advancements.paleontologist.descr", "Obtain your fist fossil");

        //creative tab
        addTab("ancientnature_tab", "Ancient Nature");


    }

    public void auto(ItemLike item) {
        add(item.asItem(), toTitleCase(ForgeRegistries.ITEMS.getKey(item.asItem()).getPath()));
    }

    public void guiTranslation(BlockEntityType<?> type){
        this.add(UtilMenu.makeTranslationKey(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(type).getPath()), toTitleCase(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(type).getPath()));
    }


  public void auto(EntityType<?> type){
        add(type, toTitleCase(ForgeRegistries.ENTITY_TYPES.getKey(type).getPath()));
  }

    /**
     * important pass in here the same name that u passed in in {@link net.reaper.ancientnature.core.init.ModCreativeModTabs#createTranslationKey(String)}
     */
    public void addTab(String tab, String translation){
        this.add(ModCreativeModTabs.createTranslationKey(tab), translation);
    }

    /**
     * this translates registry names to actual names
     * for example "fossilized_grave becomes "Fossilized Gravel" or "fosslilized_suspicious_gravel" becomes "Fosslilized Suspicious Gravel"
     *
     * @param input
     * @return
     */
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextTitleCase = true;
                titleCase.append(" ");
                continue;
            }

            if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
