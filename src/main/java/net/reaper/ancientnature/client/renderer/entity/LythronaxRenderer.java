package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.LythronaxModel;
import net.reaper.ancientnature.client.model.entity.OviraptorModel;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import net.reaper.ancientnature.common.entity.ground.OviraptorEntity;

public class LythronaxRenderer extends MobRenderer<LythronaxEntity, LythronaxModel> {
    public LythronaxRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LythronaxModel(pContext.bakeLayer(LythronaxModel.LYTHRONAX_LAYER)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(LythronaxEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/lythronax/texture_lythronax_male.png");
    }

    @Override
    public void render(LythronaxEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {



        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
