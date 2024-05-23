package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.AnomalocarisModel;
import net.reaper.ancientnature.common.entity.water.Anomalocaris;

@OnlyIn(Dist.CLIENT)
public class AnomalocarisRenderer extends MobRenderer<Anomalocaris, AnomalocarisModel> {
    ResourceLocation ANOMALOCARIS = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/anomalocaris_texture.png");
    ResourceLocation EMOLOCARIS = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/emolocaris_texture.png");

    public AnomalocarisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AnomalocarisModel(pContext.bakeLayer(AnomalocarisModel.ANOMALOCARIS_LAYER)), .6f);
    }

    @Override
    public ResourceLocation getTextureLocation(Anomalocaris pEntity) {
        return (pEntity.hasCustomName() && pEntity.getName().getString().equals("2009")) ? EMOLOCARIS : ANOMALOCARIS;
    }

    @Override
    public void render(Anomalocaris pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    protected void scale(Anomalocaris pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        if (pLivingEntity.isBaby()) {

            pMatrixStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}
