package net.reaper.ancientnature.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Saddleable;
import net.reaper.ancientnature.client.model.entity.SmartAnimalModel;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import org.jetbrains.annotations.NotNull;

public class ModSaddleLayer<T extends SmartAnimatedAnimal, M extends SmartAnimalModel<T>> extends RenderLayer<T, M> {
    private final ResourceLocation saddleLocation;

    public ModSaddleLayer(RenderLayerParent<T, M> pRenderer, ResourceLocation pSaddleLocation) {
        super(pRenderer);
        this.saddleLocation = pSaddleLocation;
    }

    public void render(@NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, @NotNull T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.isBaby()) {
            if (pLivingEntity instanceof Saddleable saddleable) {
                if (saddleable.isSaddled()) {
                    this.getParentModel().copyPropertiesTo(this.getParentModel());
                    this.getParentModel().prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
                    this.getParentModel().setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                    VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.saddleLocation));
                    this.getParentModel().renderToBuffer(pMatrixStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
