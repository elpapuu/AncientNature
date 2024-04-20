package net.reaper.ancientnature.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.reaper.ancientnature.common.blockentity.RevivalStandBlockEntity;

public class RevivalStandRenderer implements BlockEntityRenderer<RevivalStandBlockEntity> {

    protected final BlockEntityRendererProvider.Context context;

    public RevivalStandRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(RevivalStandBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (!pBlockEntity.getItem(3).isEmpty()){
            renderItem(pBlockEntity.getItem(3), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, .75d, .2d, .2d);
        }
        if (!pBlockEntity.getItem(4).isEmpty()){
            renderItem(pBlockEntity.getItem(4), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, .75d, .2d, .7d);
        }
        if (!pBlockEntity.getItem(5).isEmpty()){
            renderItem(pBlockEntity.getItem(5), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, .25d, .2d, .45d);
        }
    }

    protected void renderItem(ItemStack stack,PoseStack matrixStack,MultiBufferSource source, int packedLight, int packedOverlay, double xOffset, double yOffset, double zOffset){
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, zOffset);
        matrixStack.scale(.5f, .5f, .5f);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, packedLight, packedOverlay, matrixStack, source, Minecraft.getInstance().level, 1);
        matrixStack.popPose();
    }
}
