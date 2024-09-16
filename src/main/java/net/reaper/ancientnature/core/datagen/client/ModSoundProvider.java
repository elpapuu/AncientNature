package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModSounds;

public class ModSoundProvider extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    public ModSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, AncientNature.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.WHERE_YOUR_JOURNEY_BEGINS_DISC.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(ModSounds.WHERE_YOUR_JOURNEY_BEGINS_DISC.get().getLocation(), SoundDefinition.SoundType.SOUND).stream()).subtitle(createSubtitle(ModSounds.WHERE_YOUR_JOURNEY_BEGINS_DISC.get())));
        simple(ModSounds.ANOMALOCARIS_DEATH_1.get());
        simple(ModSounds.ANOMALOCARIS_DEATH_2.get());
        simple(ModSounds.ANOMALOCARIS_EAT_1.get());
        simple(ModSounds.ANOMALOCARIS_EAT_2.get());
        simple(ModSounds.ANOMALOCARIS_HURT_1.get());
        simple(ModSounds.ANOMALOCARIS_HURT_2.get());
        simple(ModSounds.DODO_IDLE_1.get());
        simple(ModSounds.DODO_IDLE_2.get());
        simple(ModSounds.DODO_HURT_1.get());
        simple(ModSounds.DODO_HURT_2.get());
        simple(ModSounds.DODO_DIYING.get());
        //there is no ogg file for this sound event so i excluded it from the datagen
        //simple(ModSounds.CLEANED_FOSSIL.get());
    }

    public void simple(SoundEvent event){
        add(event, simpleDefinition(event));
    }

    protected SoundDefinition simpleDefinition(SoundEvent event){
        return SoundDefinition.definition().with(SoundDefinition.Sound.sound(event.getLocation(), SoundDefinition.SoundType.SOUND).stream()).subtitle(createSubtitle(event));
    }

    public static String createSubtitle(SoundEvent event){
        return "sound." + event.getLocation().getNamespace() + "." + event.getLocation().getPath();
    }
}
