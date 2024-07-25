package net.reaper.ancientnature.common.util;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.reaper.ancientnature.common.entity.water.Anomalocaris;
import net.reaper.ancientnature.core.init.ModEntities;

public class EntityPlacementUtil {
    public  static void entityPlacement() {
        SpawnPlacements.register(ModEntities.ANOMALOCARIS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Anomalocaris::checkSurfaceWaterDinoSpawnRules);

    }
}