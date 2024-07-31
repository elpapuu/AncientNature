package net.reaper.ancientnature.client.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CameraShakeEvent extends PlayerEvent
{

    public final float intensity;
    public final int duration;

    public CameraShakeEvent(Player player, float intensity, int duration) {
        super(player);
        this.intensity = intensity;
        this.duration = duration;
    }
}
