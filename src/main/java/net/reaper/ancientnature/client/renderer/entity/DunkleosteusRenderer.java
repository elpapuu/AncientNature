package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.DunkleosteusModel;
import net.reaper.ancientnature.common.entity.water.DunkleosteusEntity;

public class DunkleosteusRenderer extends MobRenderer<DunkleosteusEntity, DunkleosteusModel> {
    public DunkleosteusRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DunkleosteusModel(pContext.bakeLayer(DunkleosteusModel.DUNKLEOSTEUS_LAYER)), 1.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(DunkleosteusEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/dunkleosteus/dunkleosteus_female.png");
    }

    @Override
    public void render(DunkleosteusEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {



        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}