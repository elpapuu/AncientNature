package net.reaper.ancientnature.common.messages.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.reaper.ancientnature.common.messages.util.EventHandlerRegistry;
import net.reaper.ancientnature.common.messages.util.EventData;

import java.util.function.Supplier;

public class MessageServerEntityEvent {
    private final BlockPos blockPos;
    private final int eventId;
    private final EventData[] eventData;

    public MessageServerEntityEvent(BlockPos pBlockPos, int pEventId, EventData... pEventData) {
        this.blockPos = pBlockPos;
        this.eventId = pEventId;
        this.eventData = pEventData;
    }

    public static void write(MessageServerEntityEvent message, FriendlyByteBuf pBuf) {
        pBuf.writeBlockPos(message.blockPos);
        pBuf.writeInt(message.eventId);
        pBuf.writeInt(message.eventData.length);
        for (EventData argument : message.eventData) {
            argument.write(pBuf);
        }
    }

    public static MessageServerEntityEvent read(FriendlyByteBuf pBuf) {
        BlockPos pos = pBuf.readBlockPos();
        int eventId = pBuf.readInt();
        int length = pBuf.readInt();
        EventData[] eventData = new EventData[length];
        for(int i = 0; i < eventData.length; ++i) {
            eventData[i] = EventData.read(pBuf);
        }
        return new MessageServerEntityEvent(pos, eventId, eventData);
    }

    public static void handle(MessageServerEntityEvent pMessage, Supplier<NetworkEvent.Context> pContext) {
        NetworkEvent.Context context = pContext.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            ServerPlayer sender = context.getSender();
            if (sender != null) {
                context.enqueueWork(() -> {
                    if (sender.level() instanceof ServerLevel serverLevel) {
                        EventHandlerRegistry.LevelEventHandler.INSTANCE.processCommonEvent(serverLevel, pMessage.blockPos, pMessage.eventId, pMessage.eventData);
                    }
                });
            }
        }
        context.setPacketHandled(true);
    }
}
