package net.reaper.ancientnature.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.reaper.ancientnature.common.block.RevivalStand;
import net.reaper.ancientnature.common.blockentity.RevivalStandBlockEntity;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModTags;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class RevivalStandRenderer implements BlockEntityRenderer<RevivalStandBlockEntity> {

    protected final BlockEntityRendererProvider.Context context;

    public RevivalStandRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(RevivalStandBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        if (!pBlockEntity.getItem(3).isEmpty()) {
            renderItem(pBlockEntity.getItem(3), pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay,
                    .5d, 2.12d, .5d, 0.4f, 3f, 0.02, 4, 0);
        }

        if (!pBlockEntity.getItem(1).isEmpty()) {
            renderItem(pBlockEntity.getItem(1), pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay,
                    .5d, 1.12d, .5d, 0.4f, 2f , 0.02, 4, 20);
        }

        if (!pBlockEntity.getItem(0).isEmpty()) {
            renderItem(pBlockEntity.getItem(0), pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay,
                    .5d, .22d, .5, 0.5f, 1f, 0.01, 4, 40);
        }
    }

    protected void renderItem(ItemStack stack, float pPartialTick, PoseStack matrixStack, MultiBufferSource source,
                              int packedLight, int packedOverlay, double xOffset,  double baseY, double zOffset,
                              float scale, float rotationSpeed, double hoverHeight, double hoverSpeed, double hoverOffset){

        matrixStack.pushPose();

        double yOffset = baseY + Math.sin((Minecraft.getInstance().level.getGameTime() + pPartialTick + hoverOffset) / hoverSpeed) * hoverHeight;
        matrixStack.translate(xOffset, yOffset, zOffset);

        float gameTime = Minecraft.getInstance().level.getGameTime() + pPartialTick;
        float angle = (gameTime % 360) * rotationSpeed;

        matrixStack.mulPose(Axis.YP.rotationDegrees(angle));
        matrixStack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
                packedLight, packedOverlay, matrixStack, source, Minecraft.getInstance().level, 1);
        matrixStack.popPose();
    }
}
