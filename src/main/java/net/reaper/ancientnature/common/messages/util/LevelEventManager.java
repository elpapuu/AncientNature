package net.reaper.ancientnature.common.messages.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LevelEventManager implements CommonEventProcessor {
    private static final EventHandlerRegistry EVENT_REGISTRY = new EventHandlerRegistry();
    public static LevelEvent ATTACK = new LevelEvent(0);
    public static LevelEvent ROAR = new LevelEvent(1);

    static {
        EVENT_REGISTRY.registerAnnotatedHandlers(new LevelEvents());
    }

    @Override
    public void onProcessEvent(ServerLevel pServerLevel, BlockPos pBlockPos, int pEventId, EventData[] pEventData) {
        EVENT_REGISTRY.handleEvent(pServerLevel, pBlockPos, pEventId, pEventData);
    }
}
