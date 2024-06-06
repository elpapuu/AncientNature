package net.reaper.ancientnature.common.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.OviraptorEntity;
import net.reaper.ancientnature.common.entity.ground.TuataraEntity;
import net.reaper.ancientnature.common.entity.water.Anomalocris;
import net.reaper.ancientnature.core.init.ModEntities;
import net.reaper.ancientnature.common.entity.water.ArandaspisEntity;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {
@SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ARANDASPIS.get(), ArandaspisEntity.createAttributes().build());
        event.put(ModEntities.TUATARA.get(), TuataraEntity.createAttributes().build());
        event.put(ModEntities.ANOMALOCRIS.get(), Anomalocris.createAttributes().build());
        event.put(ModEntities.OVIRAPTOR.get(), OviraptorEntity.createAttributes().build());
    }
}
