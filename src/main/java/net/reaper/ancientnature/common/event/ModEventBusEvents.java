package net.reaper.ancientnature.common.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import net.reaper.ancientnature.common.entity.ground.OviraptorEntity;
import net.reaper.ancientnature.common.entity.ground.TuataraEntity;
import net.reaper.ancientnature.common.entity.water.Anomalocaris;
import net.reaper.ancientnature.common.entity.water.Paranogmius;
import net.reaper.ancientnature.core.init.ModEntities;
import net.reaper.ancientnature.common.entity.water.Arandaspis;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ARANDASPIS.get(), Arandaspis.createAttributes().build());
        event.put(ModEntities.TUATARA.get(), TuataraEntity.createAttributes().build());
        event.put(ModEntities.PARANOGMIUS.get(), Paranogmius.createAttributes().build());
        event.put(ModEntities.ANOMALOCARIS.get(), Anomalocaris.createAttributes().build());
        event.put(ModEntities.OVIRAPTOR.get(), OviraptorEntity.createAttributes().build());
        event.put(ModEntities.LYTHRONAX.get(), LythronaxEntity.createAttributes().build());
    }
}
