package net.reaper.ancientnature.common.messages.util;

import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class EventHandlerRegistry {
    private static final Logger LOGGER = Logger.getLogger(EventHandlerRegistry.class.getName());
    public Map<Integer, EventProcessor> eventHandlers  = new ConcurrentHashMap<>();

    public void handleEvent(Level pLevel, BlockPos pBlockPos, int pEventId, EventData[] pEventData) {
        EventProcessor processor = eventHandlers .getOrDefault(pEventId, (blockPos, level, eventData) -> LOGGER.log(java.util.logging.Level.WARNING, "No handler found for event ID: {0}", pEventId));
        try {
            processor.handle(pBlockPos, pLevel, pEventData);
        } catch (Throwable throwable) {
            throw new ReportedException(CrashReport.forThrowable(throwable, "Error handling event ID: " + pEventId));
        }
    }

    public EventHandlerRegistry register(int pEventId, EventProcessor pProcessor) {
        this.eventHandlers.put(pEventId, pProcessor);
        return this;
    }

    public static class LevelEventHandler {
        public static LevelEventHandler INSTANCE = new LevelEventHandler();
        public Map<String, CommonEventProcessor> serverListeners = new ConcurrentHashMap<>();

        public void processCommonEvent(ServerLevel pServerLevel, BlockPos pBlockPos, int pEventId, EventData[] pEventData) {
            this.serverListeners.forEach((key, listener) -> listener.onProcessEvent(pServerLevel, pBlockPos, pEventId, pEventData));
        }

        public void addCommonListener(ResourceLocation pResourceLocation, CommonEventProcessor pProcessor) {
            this.serverListeners.put(pResourceLocation.toString(), pProcessor);
        }

        public static void registerCommonHandler(ResourceLocation pResourceLocation, CommonEventProcessor pProcessor) {
            INSTANCE.addCommonListener(pResourceLocation, pProcessor);
        }
    }

    public boolean isValidEventHandlerMethod(Method pMethod) {
        return pMethod.getParameterCount() == 3 &&
                pMethod.getParameterTypes()[0] == BlockPos.class &&
                pMethod.getParameterTypes()[1] == Level.class &&
                pMethod.getParameterTypes()[2] == EventData[].class;
    }

    public void registerAnnotatedHandlers(Object pHandlerObject) {
        Method[] methods = pHandlerObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                if (this.isValidEventHandlerMethod(method)) {
                    EventProcessor processor = (pos, level, eventData) -> {
                        method.setAccessible(true);
                        method.invoke(pHandlerObject, pos, level, eventData);
                    };
                    this.register(eventHandler.eventId(), processor);
                }
            }
        }
    }
}
