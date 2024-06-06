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
import net.reaper.ancientnature.common.entity.water.Anomalocris;
import net.reaper.ancientnature.common.entity.water.ArandaspisEntity;

@OnlyIn(Dist.CLIENT)
public class AnomalocrisRenderer extends MobRenderer<Anomalocris, AnomalocrisModel> {
    public AnomalocrisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AnomalocrisModel(pContext.bakeLayer(AnomalocrisModel.AnomalocarisLayer)), .6f);
    }

    @Override
    public ResourceLocation getTextureLocation(Anomalocris pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID,"textures/entity/anomalocaris_texture.png");
    }

    @Override
    public void render(Anomalocris pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
