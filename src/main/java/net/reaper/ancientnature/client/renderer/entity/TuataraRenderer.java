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
import net.reaper.ancientnature.client.model.entity.TuataraModel;
import net.reaper.ancientnature.common.entity.ground.TuataraEntity;

@OnlyIn(Dist.CLIENT)
public class TuataraRenderer extends MobRenderer<TuataraEntity, TuataraModel> {
    public TuataraRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TuataraModel(pContext.bakeLayer(TuataraModel.TUATARA_LAYER)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(TuataraEntity pEntity) {
        return new ResourceLocation(AncientNature.MOD_ID, "textures/entity/"+(pEntity.isMale()?"male":"female")+"_tuatara_texture.png");
    }


}
