package net.reaper.ancientnature.common.messages.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;

public class LevelEvents implements CommonEventProcessor {
    private static final EventHandlerRegistry EVENT_REGISTRY;
    public static LevelEvent ATTACK = new LevelEvent();
    public static LevelEvent ROAR = new LevelEvent();

    static {
        EVENT_REGISTRY = new EventHandlerRegistry().register(ROAR.eventId, ((pBlockPos, pLevel, pEventData) -> {
            Entity entity = pLevel.getEntity(pEventData[0].asInteger());
            if (entity instanceof LythronaxEntity lythronax) {
                lythronax.setRoarTicks(60);
            }
        }));
    }

    @Override
    public void onProcessEvent(ServerLevel pServerLevel, BlockPos pBlockPos, int pEventId, EventData[] pEventData) {
        EVENT_REGISTRY.handleEvent(pServerLevel, pBlockPos, pEventId, pEventData);
    }
}
