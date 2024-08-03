package net.reaper.ancientnature.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.kinds.IdF;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.common.entity.util.ICustomPlayerRidePos;
import net.reaper.ancientnature.common.util.RenderUtil;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PassengerLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public PassengerLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    public void render(@NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLightIn, T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pEntity.isVehicle() || pEntity.getPassengers().isEmpty()) {
            return;
        }
        pEntity.getPassengers().stream().filter(passenger -> !RenderUtil.shouldSkipRendering(true, passenger)).forEach(passenger -> {
            UUID uuid = passenger.getUUID();
            RenderUtil.hiddenEntities.remove(uuid);
            pMatrixStack.pushPose();
            if (RenderUtil.getEntityRenderer(pEntity) instanceof ICustomPlayerRidePos customRidePos) {
                customRidePos.applyRiderMatrixStack(pEntity, pMatrixStack);
            }
            RenderUtil.renderEntity(passenger, pPartialTicks, pMatrixStack, pBuffer, pPackedLightIn);
            pMatrixStack.popPose();
            RenderUtil.hiddenEntities.add(uuid);
        });
    }
}
