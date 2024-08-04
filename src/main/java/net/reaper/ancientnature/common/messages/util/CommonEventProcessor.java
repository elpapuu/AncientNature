package net.reaper.ancientnature.common.messages.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

@FunctionalInterface
public interface CommonEventProcessor {
    void onProcessEvent(ServerLevel pServerLevel, BlockPos pBlockPos, int pEventId, EventData[] pEventData);
}
