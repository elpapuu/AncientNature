package net.reaper.ancientnature.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.reaper.ancientnature.common.blockentity.RevivalStandBlockEntity;
import net.reaper.ancientnature.common.util.BurnTimeUtils;
import net.reaper.ancientnature.core.init.ModMenus;
import net.reaper.ancientnature.core.init.ModRecipes;
import net.reaper.ancientnature.core.init.ModTags;

public class RevivalStandMenu extends BaseTileEntityMenu<RevivalStandBlockEntity> {
    public RevivalStandMenu(int id, Inventory inv, RevivalStandBlockEntity tileEntity) {
        super(ModMenus.REVIVAL_STAND.get(), id, inv, tileEntity);
    }

    public RevivalStandMenu(int id, Inventory inv, FriendlyByteBuf buffer) {
        super(ModMenus.REVIVAL_STAND.get(), id, inv, buffer);
    }

    @Override
    public void init() {
        this.addSlot(new SpecialFuelSlot(this.tileEntity, 0, 17, 50, ModRecipes.REVIVAL_STAND_RECIPE.get()));//fuel slot
        this.addSlot(new Slot(this.tileEntity, 1, 17, 17));//Amber slot
        this.addSlot(new Slot(this.tileEntity, 2, 79, 17));//fossil slot

        this.addSlot(new SingletonSlot(this.tileEntity, 3, 56, 51));//egg 1
        this.addSlot(new SingletonSlot(this.tileEntity, 4, 79, 58));//egg 2
        this.addSlot(new SingletonSlot(this.tileEntity, 5, 102, 51));//egg 3

        addPlayerInventory(8, 84);

        this.addDataSlots(this.tileEntity.data);
    }



    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack slotCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            slotCopy = slotItem.copy();
            if (pIndex >= 3 && pIndex < 6) {
                if (!this.moveItemStackTo(slotItem, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotItem, slotCopy);
            } else if (pIndex >= 6) {
                if (BurnTimeUtils.getBurnTime(slotItem, ModRecipes.REVIVAL_STAND_RECIPE.get()) > 0) {
                    if (!this.moveItemStackTo(slotItem, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotItem.is(ModTags.Items.AMBER)) {
                    if (!this.moveItemStackTo(slotItem, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotItem.is(ModTags.Items.FOSSILS)) {
                    if (!this.moveItemStackTo(slotItem, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }else if (pIndex < 30) {
                    if (!this.moveItemStackTo(slotItem, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex < 39 && !this.moveItemStackTo(slotItem, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotItem, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == slotCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, slotItem);
        }

        return slotCopy;
    }

    public static class SpecialFuelSlot extends Slot {
        protected final RecipeType<?> type;

        public SpecialFuelSlot(Container pContainer, int pSlot, int pX, int pY, RecipeType<?> type) {
            super(pContainer, pSlot, pX, pY);
            this.type = type;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return BurnTimeUtils.getBurnTime(pStack, this.type) > 0;
        }
    }
}
