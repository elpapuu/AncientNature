package net.reaper.ancientnature.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.ThylacineModel;
import net.reaper.ancientnature.common.entity.ground.ThylacineEntity;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ThylacineRenderer extends MobRenderer<ThylacineEntity, ThylacineModel> {
    ResourceLocation IS_MALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_male.png");
    ResourceLocation FEMALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_female.png");
    ResourceLocation BENJAMIN = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_benjamin.png");
    public ThylacineRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ThylacineModel(pContext.bakeLayer(ThylacineModel.THYLACINE_LAYER)), 0.2f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThylacineEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_"+(pEntity.isMale()?"male":"female")+".png").toString().equals(pEntity.hasCustomName() && pEntity.getName().getString().equals("Benjamin")) ? BENJAMIN : IS_MALE;
    }
}
