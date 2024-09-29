package net.reaper.ancientnature.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class ScarySkull extends Item implements Equipable {

    public ScarySkull(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

}
