package net.reaper.ancientnature.common.entity.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.common.util.EntityUtils;
import net.reaper.ancientnature.common.util.ScreenShakeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IShakeScreenOnStep {
    float getShakePower();

    default float getMaxScreenShake() {
        return 0.7F;
    }

    default boolean canEntityShake(@NotNull LivingEntity pEntity) {
        return !pEntity.isInFluidType() && pEntity.onGround();
    }

    private float calculateShakeIntensity(float pAnimationSpeed) {
        float animationFactor = 1.0F + pAnimationSpeed * 0.3F;
        return 0.2F * animationFactor;
    }

    default void handleScreenShake() {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            List<LivingEntity> entityList = EntityUtils.getEntitiesAroundSelf(LivingEntity.class, localPlayer, 8.0F, 8.0F, false);
            if (entityList != null && !entityList.isEmpty())
            for (LivingEntity entity : entityList) {
                if (canEntityShake(entity) && EntityUtils.isEntityStepping(entity, getShakeFrequency(), calculateShakeIntensity(entity.walkAnimation.speed())) && entity != localPlayer){
                    float distanceFactor = getShakeDistance() / entity.distanceTo(localPlayer) / 8.0F;
                    float shakePower = Math.min(distanceFactor * getShakePower(), getMaxScreenShake());
                    ScreenShakeUtils.shakeScreen(10, shakePower);
                }
            }
        }
    }

    float getShakeFrequency();

    float getShakeDistance();
}
