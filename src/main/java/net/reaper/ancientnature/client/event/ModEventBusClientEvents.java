package net.reaper.ancientnature.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.AnomalocrisModel;
import net.reaper.ancientnature.client.model.entity.ArandaspisModel;
import net.reaper.ancientnature.client.model.entity.OviraptorModel;
import net.reaper.ancientnature.client.model.entity.TuataraModel;
import net.reaper.ancientnature.client.renderer.blockentity.RevivalStandRenderer;
import net.reaper.ancientnature.client.renderer.entity.AnomalocrisRenderer;
import net.reaper.ancientnature.client.renderer.entity.ArandaspisRenderer;
import net.reaper.ancientnature.client.renderer.entity.OviraptorRenderer;
import net.reaper.ancientnature.client.renderer.entity.TuataraRenderer;
import net.reaper.ancientnature.client.screens.RevivalStandScreen;
import net.reaper.ancientnature.common.particle.RevivalStandParticle;
import net.reaper.ancientnature.core.init.ModBlockEntities;
import net.reaper.ancientnature.core.init.ModEntities;
import net.reaper.ancientnature.core.init.ModMenus;
import net.reaper.ancientnature.core.init.ModParticles;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ArandaspisModel.ARANDASPIS_LAYER, ArandaspisModel::createBodyLayer);
        event.registerLayerDefinition(TuataraModel.TUATARA_LAYER, TuataraModel::createBodyLayer);
        event.registerLayerDefinition(AnomalocrisModel.AnomalocarisLayer, AnomalocrisModel::createBodyLayer);
        event.registerLayerDefinition(OviraptorModel.OviraptorLayer, OviraptorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderes(EntityRenderersEvent.RegisterRenderers event) {
        //entities
        event.registerEntityRenderer(ModEntities.ARANDASPIS.get(), ArandaspisRenderer::new);
        event.registerEntityRenderer(ModEntities.ARANDASPIS.get(), ArandaspisRenderer::new);
        event.registerEntityRenderer(ModEntities.ANOMALOCRIS.get(), AnomalocrisRenderer::new);
        event.registerEntityRenderer(ModEntities.TUATARA.get(), TuataraRenderer::new);
        event.registerEntityRenderer(ModEntities.OVIRAPTOR.get(), OviraptorRenderer::new);
        //block entities
        event.registerBlockEntityRenderer(ModBlockEntities.REVIVAL_STAND.get(), RevivalStandRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.REVIVAL_STAND_PARTICLE.get(), RevivalStandParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
        MenuScreens.register(ModMenus.REVIVAL_STAND.get(), RevivalStandScreen::new);
    }

}
