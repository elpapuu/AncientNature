package net.reaper.ancientnature.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.AnomalocrisModel;
import net.reaper.ancientnature.common.entity.water.Anomalocris;

@OnlyIn(Dist.CLIENT)
public class AnomalocrisRenderer extends MobRenderer<Anomalocris, AnomalocrisModel> {
    public AnomalocrisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AnomalocrisModel(pContext.bakeLayer(AnomalocrisModel.LAYER_LOCATION)), .6f);
    }

    @Override
    public ResourceLocation getTextureLocation(Anomalocris pEntity) {
        return AncientNature.entityTexture("anomalocaris_texture.png");
    }
}
