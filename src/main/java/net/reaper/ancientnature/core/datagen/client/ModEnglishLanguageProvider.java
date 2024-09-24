package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
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
        auto(ModItems.LIZARD_AMBER.get());
        auto(ModItems.MOSQUITO_AMBER.get());

        auto(ModItems.CAMBRIAN_FOSSIL.get());
        auto(ModItems.ANOMALOCARIS_FOSSIL.get());
        auto(ModItems.CARBONIFEROUS_FOSSIL.get());
        auto(ModItems.DEEPSLATE_PERMIAN_FOSSIL.get());
        auto(ModItems.MUDDY_PERMIAN_FOSSIL.get());
        auto(ModItems.STONE_PERMIAN_FOSSIL.get());
        auto(ModItems.CRETACEOUS_FOSSIL.get());
        auto(ModItems.QUATERNARY_FOSSIL.get());
        auto(ModItems.DEVONIAN_FOSSIL.get());
        auto(ModItems.ARANDASPIS_FOSSIL.get());
        auto(ModItems.DODO_FOSSIL.get());
        auto(ModItems.LYTHRONAX_FOSSIL.get());
        auto(ModItems.PARANOGMIUS_FOSSIL.get());

        auto(ModItems.BLOOD_DAGGER.get());

        auto(ModItems.ROPE.get());
        auto(ModItems.LYTHRONAX_TEETH.get());

        auto(ModItems.LYTHRONAX_SADDLE.get());

        auto(ModItems.RAW_DODO.get());
        auto(ModItems.COOKED_DODO.get());
        auto(ModItems.RAW_PARANOGMIUS.get());
        auto(ModItems.COOKED_PARANOGMIUS.get());

        auto(ModItems.FISH_ROE.get());
        auto(ModItems.ARANDASPIS_BUCKET.get());
        auto(ModItems.ANOMALOCARIS_BUCKET.get());

        auto(ModItems.ARANDASPIS_SPAWN_EGG.get());
        auto(ModItems.ANOMALOCARIS_SPAWN_EGG.get());
        auto(ModItems.DODO_SPAWN_EGG.get());
        auto(ModItems.HORSESHOE_CRAB_SPAWN_EGG.get());
        auto(ModItems.LYTHRONAX_SPAWN_EGG.get());
        auto(ModItems.TUATARA_SPAWN_EGG.get());
        auto(ModItems.TREX_SPAWN_EGG.get());
        auto(ModItems.PARANOGMIUS_SPAWN_EGG.get());

        auto(ModBlocks.ARANDASPIS_ROE.get());
        auto(ModBlocks.PARANOGMIUS_ROE.get());
        auto(ModBlocks.ANOMALOCARIS_EGGS.get());
        auto(ModBlocks.REVIVAL_STAND.get());
        auto(ModBlocks.MUD_WITH_FOSSILS.get());
        auto(ModBlocks.DEEPSLATE_AMBER.get());
        auto(ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
        auto(ModBlocks.DEEPSLATE_DEVONIAN_FOSSIL.get());
        auto(ModBlocks.DEEPSLATE_CARBONIFEROUS.get());
        auto(ModBlocks.DEEPSLATE_PERMIAN_FOSSIL.get());
        auto(ModBlocks.STONE_PERMIAN_FOSSIL.get());
        auto(ModBlocks.CRETACEOUS_FOSSILS.get());
        auto(ModBlocks.QUATERNARY_FOSSILS.get());
        auto(ModBlocks.STONE_AMBER.get());

        auto(ModItems.WHERE_YOUR_JOURNEY_BEGINS_MUSIC_DISC.get());

        auto(ModEntities.ARANDASPIS.get());
        auto(ModEntities.ANOMALOCARIS.get());
        auto(ModEntities.HORSESHOE_CRAB.get());
        auto(ModEntities.DODO.get());
        auto(ModEntities.TUATARA.get());
        auto(ModEntities.DUNKLEOSTEUS.get());
        auto(ModEntities.PARANOGMIUS.get());
        auto(ModEntities.CITIPATI.get());
        auto(ModEntities.LYTHRONAX.get());
        auto(ModEntities.THYLACINE.get());
        auto(ModEntities.TREX.get());

        guiTranslation(ModBlockEntities.REVIVAL_STAND_ENTITY.get());

        //advancements
        add("advancements.secrets_of_nature.title", "Secrets of Nature");
        add("advancements.secrets_of_nature.descr", "Clean a fossil and discover what´s inside!");
        add("advancements.paleontologist.title", "Paleontologist");
        add("advancements.paleontologist.descr", "Obtain your first fossil");
        add("advancements.life_finds_a_way.title", "Life finds a way");
        add("advancements.life_finds_a_way.descr", "Obtain any kind of ancient egg");
        add("advancements.unusual_potions.title", "Unusual Potions");
        add("advancements.unusual_potions.descr", "I think this isnt for potions");

        //effects
        add("effect.ancientnature.bleeding", "Bleeding");

        //subtitles
        add(ModSounds.CLEANED_FOSSIL.get(), "Fossil cleaning");
        add(ModSounds.ANOMALOCARIS_EAT_1.get(), "Anomalocaris eating");
        add(ModSounds.ANOMALOCARIS_EAT_2.get(), "Anomalocaris eating");
        add(ModSounds.ANOMALOCARIS_HURT_1.get(), "Anomalocaris hurt");
        add(ModSounds.ANOMALOCARIS_HURT_2.get(), "Anomalocaris hurt");
        add(ModSounds.ANOMALOCARIS_DEATH_1.get(), "Anomalocaris dying");
        add(ModSounds.ANOMALOCARIS_DEATH_2.get(), "Anomalocaris dying");
        add(ModSounds.WHERE_YOUR_JOURNEY_BEGINS_DISC.get(), "Where Your Journey Begins");

        //creative tab
        addTab("ancientnature_tab", "Ancient Nature");

        //JEI
        add("jei.category.revival_stand_recipe", "Revival Stand");
    }


    public void add(SoundEvent soundEvent, String translation){
        add(ModSoundProvider.createSubtitle(soundEvent), translation);
        add("item." + ModSoundProvider.createSubtitle(soundEvent) + ".desc", translation);
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
     * important pass in here the same name that u passed in in {@link ModCreativeModTabs#createTranslationKey(String)}
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
