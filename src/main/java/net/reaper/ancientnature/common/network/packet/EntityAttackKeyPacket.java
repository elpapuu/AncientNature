package net.reaper.ancientnature.common.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.reaper.ancientnature.common.entity.util.IMouseInput;

import java.util.function.Supplier;

public class EntityAttackKeyPacket {
    public int vehicleId;
    public int riderId;

    public EntityAttackKeyPacket(int pVehicleId) {
        this.vehicleId = pVehicleId;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        this.riderId = localPlayer != null ? Minecraft.getInstance().player.getId() : -1;
    }

    public static EntityAttackKeyPacket read(FriendlyByteBuf pBuf) {
        return new EntityAttackKeyPacket(pBuf.readInt());
    }

    public static void write(EntityAttackKeyPacket pMessage, FriendlyByteBuf pBuf) {
        pBuf.writeInt(pMessage.vehicleId);
    }

    public static void handle(EntityAttackKeyPacket pMessage, Supplier<NetworkEvent.Context> pContext) {
        pContext.get().enqueueWork(() -> {
            Player senderPlayer  = pContext.get().getSender();
            if (pContext.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                senderPlayer  = Minecraft.getInstance().player;
            }
            if (senderPlayer  != null) {
                Entity vehicle  = senderPlayer .level().getEntity(pMessage.vehicleId);
                Entity rider = senderPlayer .level().getEntity(pMessage.riderId);
                if (vehicle instanceof IMouseInput mouseInput && rider instanceof Player && rider.isPassengerOfSameVehicle(vehicle)) {
                    mouseInput.onMouseClick(0);
                }
            }
        });
        pContext.get().setPacketHandled(true);
    }
}
