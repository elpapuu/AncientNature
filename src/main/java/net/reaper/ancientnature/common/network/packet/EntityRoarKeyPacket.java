package net.reaper.ancientnature.common.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.reaper.ancientnature.common.entity.util.IMouseInput;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityRoarKeyPacket {
    public int vehicleId;
    public int riderId;

    public <T extends LivingEntity> EntityRoarKeyPacket(int pVehicleId) {
        this.vehicleId = pVehicleId;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        this.riderId = localPlayer != null ? Minecraft.getInstance().player.getId() : -1;
    }

    public static EntityRoarKeyPacket read(FriendlyByteBuf pBuf) {
        return new EntityRoarKeyPacket(pBuf.readInt());
    }

    public static void write(EntityRoarKeyPacket pMessage, FriendlyByteBuf pBuf) {
        pBuf.writeInt(pMessage.vehicleId);
    }

    public static void handle(EntityRoarKeyPacket pMessage, Supplier<NetworkEvent.Context> pContext) {
        pContext.get().enqueueWork(() -> {
            Player senderPlayer  = pContext.get().getSender();
            if (pContext.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                senderPlayer  = Minecraft.getInstance().player;
            }
            if (senderPlayer  != null) {
                Entity vehicle  = senderPlayer .level().getEntity(pMessage.vehicleId);
                Entity rider = senderPlayer .level().getEntity(pMessage.riderId);
                if (vehicle instanceof IMouseInput mouseInput && rider instanceof Player && rider.isPassengerOfSameVehicle(vehicle)) {
                    mouseInput.onMouseClick(1);
                }
            }
        });
        pContext.get().setPacketHandled(true);
    }
}
