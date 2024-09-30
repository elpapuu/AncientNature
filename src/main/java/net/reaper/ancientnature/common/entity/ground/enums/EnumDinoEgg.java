package net.reaper.ancientnature.common.entity.ground.enums;

import net.minecraft.ChatFormatting;

public enum EnumDinoEgg
{
    DEFAULT(ChatFormatting.AQUA),
    BROWN(ChatFormatting.BOLD);
    ChatFormatting color;
    EnumDinoEgg(ChatFormatting color) {
        this.color = color;
    }

    public static EnumDinoEgg byMetadata(int meta) {
        EnumDinoEgg i = values()[meta];
        return i == null ? BROWN : i;
    }
}
