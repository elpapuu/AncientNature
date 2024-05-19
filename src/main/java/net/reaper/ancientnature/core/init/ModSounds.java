package net.reaper.ancientnature.core.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AncientNature.MOD_ID);
    // BELOW PLEASE

    public static final RegistryObject<SoundEvent> CLEANED_FOSSIL = registerSoundEvents("cleaned_fossil");
    public static final RegistryObject<SoundEvent> ANOMALOCARIS_EAT_1 = registerSoundEvents("anomalocaris_eat_1");
    public static final RegistryObject<SoundEvent> ANOMALOCARIS_EAT_2 = registerSoundEvents("anomalocaris_eat_2");
    public static final RegistryObject<SoundEvent> ANOMALOCARIS_HURT_1 = registerSoundEvents("anomalocaris_hurt_1");
    public static final RegistryObject<SoundEvent> ANOMALOCARIS_HURT_2 = registerSoundEvents("anomalocaris_hurt_2");
    public static final RegistryObject<SoundEvent> ANOMALOCARIS_DEATH_1 = registerSoundEvents("anomalocaris_death_1");
    public static final RegistryObject<SoundEvent> ANOMALOCARIS_DEATH_2 = registerSoundEvents("anomalocaris_death_2");

    // ABOVE PLEASE
    private static  RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name,() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AncientNature.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
