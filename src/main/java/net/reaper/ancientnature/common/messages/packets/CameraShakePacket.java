package net.reaper.ancientnature.common.messages.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraShakePacket
{
    public CameraShakePacket() {
    }

    public CameraShakePacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().gameRenderer.displayItemActivation(null); // Simple camera shake
        });
        return true;
    }
}
