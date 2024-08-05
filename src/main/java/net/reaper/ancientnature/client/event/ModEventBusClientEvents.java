package net.reaper.ancientnature.client.event;

import  net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.*;
import net.reaper.ancientnature.client.model.entity.lythronax.LythronaxBabyModel;
import net.reaper.ancientnature.client.model.entity.lythronax.LythronaxModel;
import net.reaper.ancientnature.client.model.layer.ModModelLayers;
import net.reaper.ancientnature.client.renderer.blockentity.RevivalStandRenderer;
import net.reaper.ancientnature.client.renderer.entity.*;
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
        event.registerLayerDefinition(DunkleosteusModel.DUNKLEOSTEUS_LAYER, DunkleosteusModel::createBodyLayer);
        event.registerLayerDefinition(AnomalocarisModel.ANOMALOCARIS_LAYER, AnomalocarisModel::createBodyLayer);
        event.registerLayerDefinition(ParanogmiusModel.PARANOGMIUS_LAYER, ParanogmiusModel::createBodyLayer);
        event.registerLayerDefinition(CitipatiModel.CitipatiLayer, CitipatiModel::createBodyLayer);
        event.registerLayerDefinition(LythronaxModel.LYTHRONAX_LAYER, LythronaxModel::createBodyLayer);
        event.registerLayerDefinition(DodoModel.DODO_LAYER, DodoModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TREX_LAYER, TRexModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BABY_LYTHRONAX_LAYER, LythronaxBabyModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderes(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ARANDASPIS.get(), ArandaspisRenderer::new);
        event.registerEntityRenderer(ModEntities.ARANDASPIS.get(), ArandaspisRenderer::new);
        event.registerEntityRenderer(ModEntities.ANOMALOCARIS.get(), AnomalocarisRenderer::new);
        event.registerEntityRenderer(ModEntities.PARANOGMIUS.get(), ParanogmiusRenderer::new);
        event.registerEntityRenderer(ModEntities.TUATARA.get(), TuataraRenderer::new);
        event.registerEntityRenderer(ModEntities.DUNKLEOSTEUS.get(), DunkleosteusRenderer::new);
        event.registerEntityRenderer(ModEntities.CITIPATI.get(), CitipatiRenderer::new);
        event.registerEntityRenderer(ModEntities.LYTHRONAX.get(), LythronaxRenderer::new);
        event.registerEntityRenderer(ModEntities.TREX.get(), TRexRenderer::new);
        event.registerEntityRenderer(ModEntities.DODO.get(), pContext -> new SmartAnimalRenderer<>(pContext, new DodoModel(pContext.bakeLayer(DodoModel.DODO_LAYER))));
        event.registerBlockEntityRenderer(ModBlockEntities.REVIVAL_STAND_ENTITY.get(), RevivalStandRenderer::new);
    }

    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.DODO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
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
