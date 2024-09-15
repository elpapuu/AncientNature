package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.TutuaraModel;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import net.reaper.ancientnature.common.entity.ground.TuataraEntity;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TuataraRenderer extends MobRenderer<TuataraEntity, TutuaraModel> {
    ResourceLocation IS_MALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/tuatara/tuatara_male.png");
    ResourceLocation FEMALE = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/tuatara/tuatara_female.png");
    public TuataraRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TutuaraModel(pContext.bakeLayer(TutuaraModel.TUATARA_LAYER)), 0.2f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TuataraEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/tuatara/tuatara_"+(pEntity.isMale()?"male":"female")+".png");
    }
}
