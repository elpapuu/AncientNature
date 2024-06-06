package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.AnomalocrisModel;
import net.reaper.ancientnature.client.model.entity.ArandaspisModel;
import net.reaper.ancientnature.common.entity.water.ArandaspisEntity;
@OnlyIn(Dist.CLIENT)
public class ArandaspisRenderer extends MobRenderer<ArandaspisEntity, ArandaspisModel> {
    public ArandaspisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ArandaspisModel(pContext.bakeLayer(ArandaspisModel.ARANDASPIS_LAYER)), 0.6f);
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
