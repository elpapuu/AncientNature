package net.reaper.ancientnature.common.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.multipart.EntityMultiPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntityUtil
{
    public static void updatePart(@Nullable final EntityMultiPart part, @NotNull final LivingEntity parent) {
        if (part == null || !(parent.level() instanceof ServerLevel serverLevel) || parent.isRemoved()) {
            return;
        }

        if (!part.shouldContinuePersisting()) {
            UUID uuid = part.getUUID();
            Entity existing = serverLevel.getEntity(uuid);

            if (existing != null && existing != part) {
                while (serverLevel.getEntity(uuid) != null) {
                    uuid = Mth.createInsecureUUID(parent.getRandom());
                }

                AncientNature.LOGGER.debug("Updated the UUID of [{}] due to a clash with [{}]", part, existing);
            }

            part.setUUID(uuid);
            serverLevel.addFreshEntity(part);
        }

        part.setParent(parent);
    }
}
