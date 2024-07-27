package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.reaper.ancientnature.client.model.entity.SmartAnimalModel;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;

public class SmartAnimalRenderer<T extends SmartAnimatedAnimal> extends MobRenderer<T,SmartAnimalModel<T>> {
    SmartAnimalModel<T> model;
    public SmartAnimalRenderer(EntityRendererProvider.Context pContext, SmartAnimalModel<T> model) {
        super(pContext,model, model.shadowRadius());
        this.model = model;
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return model.getTexture(pEntity);
    }

    protected void scale(T pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        float F = pLivingEntity.isBaby() ? 0.5F : 1.0F;
        pMatrixStack.scale(F, F, F);
        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}
