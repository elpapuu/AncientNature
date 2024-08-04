package net.reaper.ancientnature.common.messages;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.reaper.ancientnature.common.messages.packets.*;
import net.reaper.ancientnature.common.network.packet.EntityAttackKeyPacket;
import net.reaper.ancientnature.common.network.packet.EntityRoarKeyPacket;
import net.reaper.ancientnature.common.messages.packets.MessageServerEntityEvent;

public class NetworkHandler
{
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("ancientnature", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, CameraShakePacket.class, CameraShakePacket::toBytes, CameraShakePacket::new, CameraShakePacket::handle);
        CHANNEL.registerMessage(id++, MessageMultipartInteract.class, MessageMultipartInteract::write, MessageMultipartInteract::read, MessageHandler.handle(MessageMultipartInteract.Handler::handle));
        CHANNEL.registerMessage(id++, MessageSyncPath.class, MessageSyncPath::write, MessageSyncPath::read, MessageSyncPath::handle);
        CHANNEL.registerMessage(id++, MessageSyncPathReached.class, MessageSyncPathReached::write, MessageSyncPathReached::read, MessageSyncPathReached::handle);
        CHANNEL.registerMessage(id++, EntityRoarKeyPacket.class, EntityRoarKeyPacket::write, EntityRoarKeyPacket::read, EntityRoarKeyPacket::handle);
        CHANNEL.registerMessage(id++, EntityAttackKeyPacket.class, EntityAttackKeyPacket::write, EntityAttackKeyPacket::read, EntityAttackKeyPacket::handle);
        CHANNEL.registerMessage(id++, MessageServerEntityEvent.class, MessageServerEntityEvent::write, MessageServerEntityEvent::read, MessageServerEntityEvent::handle);
    }

    public static <MSG> void sendMSGToPlayer(MSG message, ServerPlayer player) {
        CHANNEL.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
    public static <MSG> void sendMSGToServer(MSG message) {
        CHANNEL.sendToServer(message);
    }

    public static <MSG> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            CHANNEL.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
