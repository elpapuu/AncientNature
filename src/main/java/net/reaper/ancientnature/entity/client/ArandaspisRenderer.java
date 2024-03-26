package net.reaper.ancientnature.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.entity.custom.ArandaspisEntity;

public class ArandaspisRenderer extends MobRenderer<ArandaspisEntity,ArandaspisModel<ArandaspisEntity>> {
    public ArandaspisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ArandaspisModel<>(pContext.bakeLayer(ModModelLayers.ARANDASPIS_LAYER)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(ArandaspisEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/arandaspis_texture.png");
    }

    @Override
    public void render(ArandaspisEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
