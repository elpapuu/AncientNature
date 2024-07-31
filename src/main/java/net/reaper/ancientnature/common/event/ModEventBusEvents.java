package net.reaper.ancientnature.common.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.*;
import net.reaper.ancientnature.common.entity.water.*;
import net.reaper.ancientnature.core.init.ModEntities;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ARANDASPIS.get(), Arandaspis.createAttributes().build());
        event.put(ModEntities.ANOMALOCARIS.get(), Anomalocaris.createAttributes().build());
        event.put(ModEntities.CITIPATI.get(), CitipatiEntity.createAttributes().build());
        event.put(ModEntities.HORSESHOE_CRAB.get(), HorseshoeCrabEntity.createAttributes().build());
        event.put(ModEntities.DUNKLEOSTEUS.get(), DunkleosteusEntity.createAttributes().build());
        event.put(ModEntities.DODO.get(), DodoEntity.createAttributes().build());
        event.put(ModEntities.TUATARA.get(), TuataraEntity.createAttributes().build());
        event.put(ModEntities.PARANOGMIUS.get(), Paranogmius.createAttributes().build());
        event.put(ModEntities.LYTHRONAX.get(), LythronaxEntity.createAttributes().build());
        event.put(ModEntities.TREX.get(), TRexEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.HORSESHOE_CRAB.get(),
                SpawnPlacements.Type.IN_WATER,
                Heightmap.Types.WORLD_SURFACE,
                HorseshoeCrabEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );
    }
}
