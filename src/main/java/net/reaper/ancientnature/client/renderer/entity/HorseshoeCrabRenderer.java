package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.HorseshoeCrabModel;
import net.reaper.ancientnature.common.entity.water.HorseshoeCrabEntity;

public class HorseshoeCrabRenderer extends MobRenderer<HorseshoeCrabEntity, HorseshoeCrabModel> {
    public HorseshoeCrabRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HorseshoeCrabModel(pContext.bakeLayer(HorseshoeCrabModel.HORSESHOE_LAYER)), 0.8f);
    }

    @Override
    public ResourceLocation getTextureLocation(HorseshoeCrabEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/horseshoe_crab/horseshoecrab.png");
    }

    @Override
    public void render(HorseshoeCrabEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {



        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
