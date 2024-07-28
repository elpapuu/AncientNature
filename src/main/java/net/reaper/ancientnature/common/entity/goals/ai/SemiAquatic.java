package net.reaper.ancientnature.common.entity.goals.ai;

import net.minecraft.server.level.ServerLevel;
import net.reaper.ancientnature.common.entity.water.HorseshoeCrabEntity;
import org.jetbrains.annotations.Nullable;

public interface SemiAquatic {

    boolean shouldEnterWater();

    boolean shouldLeaveWater();

    boolean isInWaterRainOrBubbleColumn();

    boolean shouldStopMoving();

    int getWaterSearchRange();

}