package net.reaper.ancientnature.common.entity.ground.enums;

import net.minecraft.ChatFormatting;

public enum TRexEnum
{
    NORMAL("normal",ChatFormatting.BLUE),
    BROWN("brown", ChatFormatting.BOLD);
    private final String name;
    private final ChatFormatting color;
    TRexEnum(String name, ChatFormatting color){
        this.name = name;
        this.color = color;
    }
}
