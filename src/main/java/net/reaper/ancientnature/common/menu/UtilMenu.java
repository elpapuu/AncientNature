package net.reaper.ancientnature.common.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.reaper.ancientnature.AncientNature;

import java.util.function.Predicate;

public abstract class UtilMenu extends AbstractContainerMenu {

    public static String makeTranslationKey(String name){
        return "menu." + AncientNature.MOD_ID + "." + name;
    }

    protected Inventory playerInventory;

    public UtilMenu(MenuType<?> type, int id, Inventory inv) {
        super(type, id);
        this.playerInventory = inv;
    }

    protected int addHorizontalSlots(Container handler, int Index, int x, int y, int amount, int distanceBetweenSlots) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }

    protected int addSlotField(Container handler, int StartIndex, int x, int y, int horizontalAmount,
                               int horizontalDistance, int verticalAmount, int VerticalDistance) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    protected <T extends Container> int addHorizontalSlots(T handler, int Index, int x, int y, int amount, int distanceBetweenSlots, SlotFunction<T> provider) {
        for (int i = 0; i < amount; i++) {
            addSlot(provider.makeSlot(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }

    protected <T extends Container> int addSlotField(T handler, int StartIndex, int x, int y, int horizontalAmount,
                                                     int horizontalDistance, int verticalAmount, int VerticalDistance, SlotFunction<T> provider) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance, provider);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    protected void addPlayerInventory(int x, int y) {
        // the Rest
        addSlotField(playerInventory, 9, x, y, 9, 18, 3, 18);
        y += 58;
        // Hotbar
        addHorizontalSlots(playerInventory, 0, x, y, 9, 18);
    }

    public static interface SlotFunction<T extends Container> {
        Slot makeSlot(T container, int index, int x, int y);
    }

    /**
     *
     * @return the first index in which a player slot is found
     */
    protected int getPlayerBeginSlots() {
        for (int i = 0; i < this.slots.size(); i++) {
			if (this.slots.get(i).container instanceof Inventory)
                return i;
        }

        return -1;
    }

    protected static class LockedSlot extends Slot {

        public LockedSlot(Container inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return false;
        }

    }

    protected static class FilterSlot extends Slot{
        protected final Predicate<ItemStack> validation;

        public FilterSlot(Container pContainer, int pSlot, int pX, int pY, Predicate<ItemStack> validation) {
            super(pContainer, pSlot, pX, pY);
            this.validation = validation;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return this.validation.test(pStack);
        }
    }

    protected static class SingletonSlot extends Slot{

        public SingletonSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }
}
