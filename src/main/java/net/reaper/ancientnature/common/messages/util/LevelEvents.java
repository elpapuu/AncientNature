package net.reaper.ancientnature.common.messages.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import net.reaper.ancientnature.common.util.EntityUtils;

public class LevelEvents {
    @EventHandler(eventId = 0)
    public void onAttackEvent(BlockPos pBlockPos, Level pLevel, EventData[] pEventData) {
        Entity entity = pLevel.getEntity(pEventData[0].asInteger());
        float distance = pEventData[1].asFloat();
        float damage = pEventData[2].asFloat();
        if (entity instanceof LivingEntity living) {
            EntityUtils.attackByRider(living, distance, damage);
        }
    }

    @EventHandler(eventId = 1)
    public void onRoarEvent(BlockPos pBlockPos, Level pLevel, EventData[] pEventData) {
        Entity entity = pLevel.getEntity(pEventData[0].asInteger());
        if (entity instanceof LythronaxEntity lythronax) {
            //lythronax.setRoarTicks(60);
        }
    }
}
