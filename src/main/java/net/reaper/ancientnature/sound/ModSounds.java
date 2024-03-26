package net.reaper.ancientnature.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.event.ClickEventHandler;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AncientNature.MOD_ID);
    public static final RegistryObject<SoundEvent> CLEANED_FOSSIL = registerSoundEvents("cleaned_fossil");

    private static  RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name,() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AncientNature.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
