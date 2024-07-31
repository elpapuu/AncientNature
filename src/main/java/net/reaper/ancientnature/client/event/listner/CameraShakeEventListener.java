package net.reaper.ancientnature.client.event.listner;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.client.event.CameraShakeEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class CameraShakeEventListener {

    private static int shakeDuration = 0;
    private static float shakeIntensity = 0.0f;

    @SubscribeEvent
    public static void onCameraShake(CameraShakeEvent event) {
        shakeDuration = event.duration;
        shakeIntensity = event.intensity;
    }

    public static void handleCameraShake() {
        if (shakeDuration > 0) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                double shakeX = (Math.random() - 0.5) * shakeIntensity;
                double shakeY = (Math.random() - 0.5) * shakeIntensity;

                mc.player.turn((float) shakeX, (float) shakeY);
            }
            shakeDuration--;
        }
    }
}