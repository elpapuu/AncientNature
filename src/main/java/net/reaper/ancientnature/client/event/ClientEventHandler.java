package net.reaper.ancientnature.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.client.event.listner.CameraShakeEventListener;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler
{
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        CameraShakeEventListener.handleCameraShake();
    }
}
