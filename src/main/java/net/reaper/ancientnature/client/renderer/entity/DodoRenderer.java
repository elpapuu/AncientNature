package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.DodoModel;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;

public class DodoRenderer extends MobRenderer<DodoEntity, DodoModel> {
    ResourceLocation MALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/dodo/dodo_male.png");
    ResourceLocation FEMALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/dodo/dodo_male.png");
    ResourceLocation PIZZA = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/dodo/dodo_pizza.png");

    public DodoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DodoModel(pContext.bakeLayer(DodoModel.DODO_LAYER)), .6f);
    }

    @Override
    public ResourceLocation getTextureLocation(DodoEntity pEntity) {
        return (pEntity.hasCustomName() && pEntity.getName().getString().equals("Pizza")) ? PIZZA : MALE;
    }

    @Override
    public void render(DodoEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    protected void scale(DodoEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {

        float F = pLivingEntity.isBaby() ? 0.5F : 1.0F;
        pMatrixStack.scale(F, F, F);

        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}