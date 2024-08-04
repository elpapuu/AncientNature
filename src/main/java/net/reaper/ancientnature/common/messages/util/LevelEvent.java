package net.reaper.ancientnature.common.messages.util;

import java.util.concurrent.atomic.AtomicInteger;

public class LevelEvent {
    public static AtomicInteger UNIQUE_ID_IDENTIFIER = new AtomicInteger(1000);
    public int eventId;

    public LevelEvent() {
        this.eventId = UNIQUE_ID_IDENTIFIER.getAndIncrement();
    }
}
