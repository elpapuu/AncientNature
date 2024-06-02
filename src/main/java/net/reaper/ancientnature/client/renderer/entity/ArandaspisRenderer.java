package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.ArandaspisModel;
import net.reaper.ancientnature.common.entity.water.Arandaspis;
@OnlyIn(Dist.CLIENT)
public class ArandaspisRenderer extends MobRenderer<Arandaspis, ArandaspisModel> {
    private final ResourceLocation ADULT = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/arandaspis_texture.png");
    private final ResourceLocation BABY = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/arandababy_texture.png");

    public ArandaspisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ArandaspisModel(pContext.bakeLayer(ArandaspisModel.ARANDASPIS_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(Arandaspis pEntity) {
        return ADULT;
    }

    @Override
    public void render(Arandaspis pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    protected void scale(Arandaspis pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {

        float F = pLivingEntity.isBaby() ? 0.2F : 0.4F;
        pMatrixStack.scale(F, F, F);

        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}
