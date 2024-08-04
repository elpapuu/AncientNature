package net.reaper.ancientnature.common.messages.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.UUID;

public record EventData(DataType pDataType, Object pValue) {
    public void write(FriendlyByteBuf pBuf) {
        pBuf.writeEnum(this.pDataType);
        switch (this.pDataType) {
            case DOUBLE -> pBuf.writeDouble(this.asDouble());
            case FLOAT -> pBuf.writeFloat(this.asFloat());
            case INTEGER -> pBuf.writeInt(this.asInteger());
            case STRING -> pBuf.writeUtf(this.asString());
            case BOOLEAN -> pBuf.writeBoolean(this.asBoolean());
            case ITEM_STACK -> pBuf.writeItem(this.asItemStack());
            case VECTOR_3 -> {
                Vec3 vec3 = this.asVector3();
                pBuf.writeDouble(vec3.x);
                pBuf.writeDouble(vec3.y);
                pBuf.writeDouble(vec3.x);
            }
            case UUID -> {
                UUID uuid = this.asUUID();
                pBuf.writeLong(uuid.getMostSignificantBits());
                pBuf.writeLong(uuid.getLeastSignificantBits());
            }
            case COLOR -> pBuf.writeInt(this.asColor().getRGB());
            case COMPOUND -> pBuf.writeNbt(this.asCompound());
            default -> throw new IllegalArgumentException("Unsupported type: " + this.pDataType);
        }
    }

    public static EventData read(FriendlyByteBuf pBuf) {
        DataType type = pBuf.readEnum(DataType.class);
        return switch (type) {
            case DOUBLE -> new EventData(type, pBuf.readDouble());
            case FLOAT -> new EventData(type, pBuf.readFloat());
            case INTEGER -> new EventData(type, pBuf.readInt());
            case STRING -> new EventData(type, pBuf.readUtf());
            case BOOLEAN -> new EventData(type, pBuf.readBoolean());
            case ITEM_STACK -> new EventData(type, pBuf.readItem());
            case VECTOR_3 -> new EventData(type, new Vec3(pBuf.readDouble(), pBuf.readDouble(), pBuf.readDouble()));
            case UUID -> new EventData(type, new UUID(pBuf.readLong(), pBuf.readLong()));
            case COLOR -> new EventData(type, new Color(pBuf.readInt()));
            case COMPOUND -> new EventData(type, pBuf.readNbt());
        };
    }

    public ItemStack asItemStack() {
        return (ItemStack) this.pValue;
    }

    public float asFloat() {
        return (Float) this.pValue;
    }

    public Vec3 asVector3() {
        return (Vec3) this.pValue;
    }

    public int asInteger() {
        return (Integer) this.pValue;
    }

    public boolean asBoolean() {
        return (Boolean) this.pValue;
    }

    public String asString() {
        return (String) this.pValue;
    }

    public double asDouble() {
        return (Double) this.pValue;
    }

    public Color asColor() {
        return (Color) this.pValue;
    }

    public UUID asUUID() {
        return (UUID) this.pValue;
    }

    public CompoundTag asCompound() {
        return (CompoundTag) this.pValue;
    }

    public static EventData valueVector3(Vec3 pVector3) {
        return new EventData(DataType.VECTOR_3, pVector3);
    }

    public static EventData valueCompound(CompoundTag pCompound) {
        return new EventData(DataType.COMPOUND, pCompound);
    }

    public static EventData valueItemStack(ItemStack pItemStack) {
        return new EventData(DataType.ITEM_STACK, pItemStack);
    }

    public static EventData valueFloat(float pFloat) {
        return new EventData(DataType.FLOAT, pFloat);
    }

    public static EventData valueDouble(double pDouble) {
        return new EventData(DataType.DOUBLE, pDouble);
    }

    public static EventData valueInteger(int pInteger) {
        return new EventData(DataType.INTEGER, pInteger);
    }

    public static EventData valueBoolean(boolean pBoolean) {
        return new EventData(DataType.BOOLEAN, pBoolean);
    }

    public static EventData valueString(String pString) {
        return new EventData(DataType.STRING, pString);
    }

    public static EventData valueUUID(UUID pUUID) {
        return new EventData(DataType.UUID, pUUID);
    }

    public enum DataType {
        VECTOR_3,
        FLOAT,
        DOUBLE,
        INTEGER,
        STRING,
        UUID,
        COLOR,
        COMPOUND,
        BOOLEAN,
        ITEM_STACK
    }
}
