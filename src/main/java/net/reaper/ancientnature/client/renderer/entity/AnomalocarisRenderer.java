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
import net.reaper.ancientnature.client.model.entity.DodoModel;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;
import net.reaper.ancientnature.common.entity.water.Anomalocaris;

public class AnomalocarisRenderer extends MobRenderer<Anomalocaris, AnomalocarisModel> {
    ResourceLocation IS_MALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/anomalocaris/anomalocaris_male.png");
    ResourceLocation FEMALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/anomalocaris/anomalocaris_male.png");
    ResourceLocation EMO = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/anomalocaris/emolocaris_texture.png");

    public AnomalocarisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AnomalocarisModel(pContext.bakeLayer(AnomalocarisModel.ANOMALOCARIS_LAYER)), .6f);
    }

    @Override
    public ResourceLocation getTextureLocation(Anomalocaris pEntity) {
        return (pEntity.hasCustomName() && pEntity.getName().getString().equals("2009")) ? EMO : IS_MALE;
    }

    @Override
    public void render(Anomalocaris pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    protected void scale(Anomalocaris pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {

        float F = pLivingEntity.isBaby() ? 0.5F : 1.0F;
        pMatrixStack.scale(F, F, F);

        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }

}
