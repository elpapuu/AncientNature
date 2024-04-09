package net.reaper.ancientnature.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.AnomalocrisModel;
import net.reaper.ancientnature.client.renderer.entity.AnomalocrisRenderer;
import net.reaper.ancientnature.core.init.ModEntities;
import net.reaper.ancientnature.client.model.entity.ArandaspisModel;
import net.reaper.ancientnature.client.renderer.entity.ArandaspisRenderer;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ArandaspisModel.ARANDASPIS_LAYER, ArandaspisModel::createBodyLayer);
        event.registerLayerDefinition(AnomalocrisModel.LAYER_LOCATION, AnomalocrisModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderes(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.ARANDASPIS.get(), ArandaspisRenderer::new);
        event.registerEntityRenderer(ModEntities.ANOMALOCRIS.get(), AnomalocrisRenderer::new);
    }

}
