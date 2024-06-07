package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.ItemStack;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;

public class EatItemsInHandGoal extends Goal {

    private final BaseTameableDinoEntity dinoEntity;
    public EatItemsInHandGoal(BaseTameableDinoEntity dinoEntity) {

        this.dinoEntity = dinoEntity;
    }

    @Override
    public boolean canUse() {
        ItemStack itemStack = dinoEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        if(dinoEntity.getEatTimer() > 0 && itemStack.isEmpty()) {
            dinoEntity.setEatTimer(dinoEntity.getEatTimer() - 1);
            return false;
        }
        else {
            dinoEntity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            // set something for eating animation here.
            return true;
        }
    }


}
