package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.ParanogmiusModel;
import net.reaper.ancientnature.client.renderer.entity.layer.ModPassengerLayer;
import net.reaper.ancientnature.common.entity.util.ICustomPlayerRidePos;
import net.reaper.ancientnature.common.entity.water.Paranogmius;
import org.jetbrains.annotations.NotNull;

public class ParanogmiusRenderer extends MobRenderer<Paranogmius, ParanogmiusModel> implements ICustomPlayerRidePos {
    private final ResourceLocation ADULT = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/paranogmius/paranogmius_male_a.png");
    private final ResourceLocation BABY = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/paranogmius/paranogmius_baby_b.png");

    public ParanogmiusRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ParanogmiusModel(pContext.bakeLayer(ParanogmiusModel.PARANOGMIUS_LAYER)), 0.6f);
        this.addLayer(new ModPassengerLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Paranogmius pEntity) {
        return ADULT;
    }

    @Override
    public void render(Paranogmius pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public <T extends LivingEntity> void applyRiderPose(HumanoidModel<T> pHumanoidModel, @NotNull T pRider) {
        pHumanoidModel.rightArm.xRot = this.rad(-155.0F);
        pHumanoidModel.leftArm.xRot = this.rad(-155.0F);
        pHumanoidModel.rightLeg.xRot = this.rad(5.0F);
        pHumanoidModel.rightLeg.zRot = this.rad(10.0F);
        pHumanoidModel.leftLeg.xRot = this.rad(5.0F);
        pHumanoidModel.leftLeg.zRot = this.rad(-10.0F);
        pHumanoidModel.head.xRot = this.rad(-80.0F);
        pHumanoidModel.head.yRot = Mth.clamp(pHumanoidModel.head.yRot, this.rad(-35.0F), this.rad(35.0F));
    }

    @Override
    public <T extends Entity> void applyRiderMatrixStack(@NotNull T pVehicle, @NotNull PoseStack pMatrixStack) {
        this.getModel().setMatrixStack(pMatrixStack);
        pMatrixStack.translate(0.0F, 0.05F - pVehicle.getBbHeight(), 1.7F);
        pMatrixStack.mulPose(Axis.YN.rotationDegrees(180.0F));
        pMatrixStack.mulPose(Axis.XN.rotationDegrees(295.0F));
    }

    @Override
    protected void scale(Paranogmius pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        float F = pLivingEntity.isBaby() ? 0.9F : 1.08F;
        pMatrixStack.scale(F, F, F);
        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}
