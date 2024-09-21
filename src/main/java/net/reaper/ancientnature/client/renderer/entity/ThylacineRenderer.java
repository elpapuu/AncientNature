package net.reaper.ancientnature.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.SmartAnimalModel;
import net.reaper.ancientnature.client.model.entity.thylacine.ThylacineBabyModel;
import net.reaper.ancientnature.client.model.entity.thylacine.ThylacineModel;
import net.reaper.ancientnature.client.model.layer.ModModelLayers;
import net.reaper.ancientnature.common.entity.ground.ThylacineEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class ThylacineRenderer extends SmartAnimalRenderer<ThylacineEntity> {
    ResourceLocation IS_MALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_male.png");
    ResourceLocation FEMALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_female.png");
    ResourceLocation BENJAMIN = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_benjamin.png");
    public SmartAnimalModel<ThylacineEntity> babyModel;
    public static SmartAnimalModel<ThylacineEntity> adultModel;
    public ThylacineRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ThylacineModel(pContext.bakeLayer(ThylacineModel.THYLACINE_LAYER)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThylacineEntity pEntity) {
        return pEntity.isBaby() ? new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_baby.png") : super.getTextureLocation(pEntity);
    }
    @Override
    public @Nullable SmartAnimalModel<ThylacineEntity> getBabyModel(EntityRendererProvider.Context pContext) {
        return new ThylacineBabyModel(pContext.bakeLayer(ModModelLayers.BABY_THYLACINE_LAYER));
    }
}
