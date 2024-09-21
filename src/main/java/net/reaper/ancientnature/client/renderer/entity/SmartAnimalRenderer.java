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
import net.reaper.ancientnature.client.model.entity.lythronax.LythronaxBabyModel;
import net.reaper.ancientnature.client.model.layer.ModModelLayers;
import net.reaper.ancientnature.client.model.entity.thylacine.ThylacineBabyModel;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmartAnimalRenderer<T extends SmartAnimatedAnimal> extends MobRenderer<T,SmartAnimalModel<T>> {
    public SmartAnimalModel<T> smartModel;
    public SmartAnimalModel<T> babyModel;

    public SmartAnimalRenderer(EntityRendererProvider.Context pContext, SmartAnimalModel<T> pSmartModel) {
        super(pContext, pSmartModel, pSmartModel.shadowRadius());
        this.smartModel = pSmartModel;
        this.babyModel = this.getBabyModel(pContext);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
        return this.smartModel.getTexture(pEntity);
    }

    @Override
    public void render(@NotNull T pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        if (this.babyModel != null) {
            this.model = pEntity.isBaby() ? this.babyModel : this.smartModel;
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Nullable
    public SmartAnimalModel<T> getBabyModel(EntityRendererProvider.Context pContext) {
        return null;
    }
}
