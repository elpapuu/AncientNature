package net.reaper.ancientnature.common.entity.util;

import javax.annotation.Nullable;

import net.minecraft.client.model.Model;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class EntityUtils extends Entity{
    public EntityUtils(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Nullable
    public static boolean isEntityMoving(@Nullable Entity pEntity, float pThreshold, Model pModel) {
        Vec3 deltaMovement = pEntity.getDeltaMovement();
        return !(deltaMovement.length() > -pThreshold && deltaMovement.length() < pThreshold);
    }
    public static boolean isMoving(@Nullable Entity pEntity, Model pModel) {
        float velocity = (float)(Math.abs(pEntity.getDeltaMovement().x) + Math.abs(pEntity.getDeltaMovement().y)) / 2.0F;
        return velocity > 0.02F;
    }
}
