package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.LythronaxModel;
import net.reaper.ancientnature.client.model.entity.SmartAnimalModel;
import net.reaper.ancientnature.client.renderer.entity.layer.ModPassengerLayer;
import net.reaper.ancientnature.client.renderer.entity.layer.ModSaddleLayer;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import net.reaper.ancientnature.common.entity.util.ICustomPlayerRidePos;
import org.jetbrains.annotations.NotNull;

public class LythronaxRenderer extends SmartAnimalRenderer<LythronaxEntity> implements ICustomPlayerRidePos {
    public static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/lythronax/saddle_0.png");

    public LythronaxRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LythronaxModel(pContext.bakeLayer(LythronaxModel.LYTHRONAX_LAYER)));
        this.addLayer(new ModPassengerLayer<>(this));
        this.addLayer(new ModSaddleLayer<>(this, SADDLE_LOCATION));
    }

    @Override
    public <T extends LivingEntity> void applyRiderPose(HumanoidModel<T> pHumanoidModel, @NotNull T pRider) {
        pHumanoidModel.rightArm.xRot = this.rad(-75.0F);
        pHumanoidModel.rightArm.zRot = this.rad(-5.0F);
        pHumanoidModel.leftArm.xRot = this.rad(-75.0F);
        pHumanoidModel.leftArm.zRot = this.rad(5.0F);
    }

    @Override
    public <T extends Entity> void applyRiderMatrixStack(@NotNull T pEntity, @NotNull PoseStack pMatrixStack) {
        if (this.getModel() instanceof LythronaxModel model) {
            model.setMatrixStack(pMatrixStack);
        }
        pMatrixStack.translate(0.0F, 2.0F - pEntity.getBbHeight(), 0.0F);
        pMatrixStack.mulPose(Axis.YN.rotationDegrees(180.0F));
        pMatrixStack.mulPose(Axis.XN.rotationDegrees(180.0F));
    }

    @Override
    public void render(LythronaxEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
