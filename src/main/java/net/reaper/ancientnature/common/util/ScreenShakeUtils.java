package net.reaper.ancientnature.common.util;

public class ScreenShakeUtils {
    public static double shakeTicks = 0.0F;
    public static double shakePower = 0.0F;

    public static void shakeScreen(float pShakeTicks, float pShakePower) {
        shakeTicks = pShakeTicks;
        if (shakePower <= 0.0F) {
            shakePower = Math.min(pShakePower, 5.0F);
        }
    }
}
