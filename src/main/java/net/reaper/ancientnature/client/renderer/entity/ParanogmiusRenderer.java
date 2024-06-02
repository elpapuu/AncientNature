package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.ParanogmiusModel;
import net.reaper.ancientnature.common.entity.water.Paranogmius;

@OnlyIn(Dist.CLIENT)
public class ParanogmiusRenderer extends MobRenderer<Paranogmius, ParanogmiusModel> {
    private final ResourceLocation ADULT = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/paranogmius_male_a.png");
    private final ResourceLocation BABY = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/paranogmius_female_a.png");

    public ParanogmiusRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ParanogmiusModel(pContext.bakeLayer(ParanogmiusModel.PARANOGMIUS_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(Paranogmius pEntity) {
        return ADULT;
    }

    @Override
    public void render(Paranogmius pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    protected void scale(Paranogmius pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {

        float F = pLivingEntity.isBaby() ? 0.2F : 0.4F;
        pMatrixStack.scale(F, F, F);

        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}
