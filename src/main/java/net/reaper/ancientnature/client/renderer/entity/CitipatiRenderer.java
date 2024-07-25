package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.CitipatiModel;
import net.reaper.ancientnature.common.entity.ground.CitipatiEntity;


public class CitipatiRenderer extends MobRenderer<CitipatiEntity, CitipatiModel> {
    public CitipatiRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CitipatiModel(pContext.bakeLayer(CitipatiModel.CitipatiLayer)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(CitipatiEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/citipati/texture_citipati_male.png");
    }

    @Override
    public void render(CitipatiEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {



        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
