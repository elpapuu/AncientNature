package net.reaper.ancientnature.common.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

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
}
