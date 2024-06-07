package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.OviraptorModel;
import net.reaper.ancientnature.client.model.entity.TuataraModel;
import net.reaper.ancientnature.common.entity.ground.OviraptorEntity;


public class OviraptorRenderer extends MobRenderer<OviraptorEntity, OviraptorModel> {
    public OviraptorRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new OviraptorModel(pContext.bakeLayer(OviraptorModel.OviraptorLayer)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(OviraptorEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/oviraptor/texture_oviraptor_male.png");
    }

    @Override
    public void render(OviraptorEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {



        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
