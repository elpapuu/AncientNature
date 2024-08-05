package net.reaper.ancientnature.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

public class ScreenUtils {
    /**
     * this should give a scaled progress, so the progress goes from 0 to max and the result of that method will go from 0 to scale
     */
    public static int getScaledInt(int progress, int max, int scale) {
        if (max == 0)
            throw new IllegalArgumentException("the max must not be zero");
        progress = Math.min(progress, max);
        return (progress * scale) / max;
    }

    /**
     * this will render an image thats slowly appearing from bottom to top with the given progress
     */
    public static void renderImageAnimationBottomTop(GuiGraphics graphics, ResourceLocation texture, int left, int top, int u, int v, int width, int height, int progress, int maxProgress) {
        if (progress > 0) {
            int scale = getScaledInt(progress, maxProgress, height);
            graphics.blit(texture, left, top + height - scale, u, v + height - scale, width, scale);
        }
    }

    public static void renderBar(RenderGuiOverlayEvent.Pre pEvent, ResourceLocation pBar, int pX1, int pY1, int pX2, int pY2, int pProgress, float pVOffset, int pWidth, int pHeight, int pHeight1) {
        PoseStack matrixStack = pEvent.getGuiGraphics().pose();
        if (!pEvent.getOverlay().id().equals(VanillaGuiOverlay.CROSSHAIR.id())) {
            matrixStack.pushPose();
            pEvent.getGuiGraphics().blit(pBar, pX1,  pY1, 0.0F, 0.0F, pWidth, pHeight, 210, 235);
            pEvent.getGuiGraphics().blit(pBar,  pX2, pY2, 3.0F, pVOffset, pProgress, pHeight1, 210, 235);
            matrixStack.popPose();
        }
    }
}
