package net.reaper.ancientnature.common.messages.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface EventProcessor {
    void handle(BlockPos pBlockPos, Level pLevel, EventData[] pEventData) throws InvocationTargetException, IllegalAccessException;
}
