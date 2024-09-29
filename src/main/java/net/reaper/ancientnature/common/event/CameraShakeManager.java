package net.reaper.ancientnature.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber
public class CameraShakeManager {

    private static final Random RANDOM = new Random();
    private static final Map<Player, ShakeData> shakingPlayers = new HashMap<>();

    public static void init() {
        MinecraftForge.EVENT_BUS.register(CameraShakeManager.class);
    }

    public static void applyCameraShake(ServerPlayer player, int duration, float intensity) {
        shakingPlayers.put(player, new ShakeData(duration, intensity));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && shakingPlayers.containsKey(event.player)) {
            ShakeData shakeData = shakingPlayers.get(event.player);

            float yawShake = (RANDOM.nextFloat() - 0.5f) * shakeData.intensity;
            float pitchShake = (RANDOM.nextFloat() - 0.5f) * shakeData.intensity;

            float currentYaw = event.player.getYRot();
            float currentPitch = event.player.getXRot();

            float targetYaw = currentYaw + yawShake;
            float targetPitch = currentPitch + pitchShake;

            float lerpedYaw = Mth.lerp(0.5f, currentYaw, targetYaw);
            float lerpedPitch = Mth.lerp(0.5f, currentPitch, targetPitch);

            event.player.setYRot(lerpedYaw);
            event.player.setXRot(lerpedPitch);

            shakeData.duration--;

            if (shakeData.duration <= 0) {
                shakingPlayers.remove(event.player);
            }
        }

    }

    private static class ShakeData {
        int duration;
        float intensity;

        public ShakeData(int duration, float intensity) {
            this.duration = duration;
            this.intensity = intensity;
        }
    }
}
