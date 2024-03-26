package net.reaper.ancientnature.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.entity.client.ArandaspisModel;
import net.reaper.ancientnature.entity.client.ModModelLayers;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
@SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.ARANDASPIS_LAYER, ArandaspisModel::createBodyLayer);
    }

}
