package net.reaper.ancientnature.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.entity.ModEntities;
import net.reaper.ancientnature.entity.custom.ArandaspisEntity;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {
@SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ARANDASPIS.get(), ArandaspisEntity.createAttributes().build());
    }
}
