package net.reaper.ancientnature.common.entity.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ICustomPlayerRidePos  {
    default <T extends LivingEntity> void applyRiderPose(T pVehicle, HumanoidModel<T> pHumanoidModel, @NotNull T pRider) {

    }

    default <T extends Entity> void applyRiderMatrixStack(@NotNull T pVehicle, @NotNull PoseStack pMatrixStack) {

    }

    default float rad(float f) {
        return (float) Math.toRadians(f);
    }
}
