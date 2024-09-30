package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.WormModel;
import net.reaper.ancientnature.common.entity.ground.WormEntity;

@OnlyIn(Dist.CLIENT)
public class WormRenderer extends SmartAnimalRenderer<WormEntity> {
    public WormRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WormModel(pContext.bakeLayer(WormModel.WORM_LAYER)));
    }

    @Override
    public ResourceLocation getTextureLocation(WormEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/worm/worm.png");
    }
    protected void scale(WormEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {

        float F = pLivingEntity.isBaby() ? 0.5F : 1.0F;
        pMatrixStack.scale(F, F, F);

        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}