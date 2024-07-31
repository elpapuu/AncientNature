package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.item.ItemEntity;
import net.reaper.ancientnature.common.entity.ground.AbstractDinoAnimal;

import java.util.EnumSet;
import java.util.List;

public class PickupFoodGoal extends Goal {
    private final AbstractDinoAnimal entity;
    private static final double PICKUP_RADIUS = 10.0D;

    public PickupFoodGoal(AbstractDinoAnimal entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (entity.isSleeping() || entity.isAttacking() || entity.isRoaring()) {
            return false;
        }

        List<ItemEntity> items = entity.level().getEntitiesOfClass(ItemEntity.class, new AABB(entity.blockPosition()).inflate(PICKUP_RADIUS), itemEntity -> itemEntity.getItem().is(Items.COOKED_BEEF));
        return !items.isEmpty();
    }

    @Override
    public void start() {
        List<ItemEntity> items = entity.level().getEntitiesOfClass(ItemEntity.class, new AABB(entity.blockPosition()).inflate(PICKUP_RADIUS), itemEntity -> itemEntity.getItem().is(Items.COOKED_BEEF));
        if (!items.isEmpty()) {
            entity.getNavigation().moveTo(items.get(0), 1.0D);
        }
    }

    @Override
    public void tick() {
        List<ItemEntity> items = entity.level().getEntitiesOfClass(ItemEntity.class, new AABB(entity.blockPosition()).inflate(PICKUP_RADIUS), itemEntity -> itemEntity.getItem().is(Items.COOKED_BEEF));
        if (!items.isEmpty()) {
            entity.getNavigation().moveTo(items.get(0), 1.0D);

            if (entity.distanceToSqr(items.get(0)) < 2.0D) {
                ItemStack stack = items.get(0).getItem();
                entity.setItemSlot(EquipmentSlot.MAINHAND, stack);
                items.get(0).remove(Entity.RemovalReason.DISCARDED);
                entity.setEating(true);
            }
        }
    }
}
