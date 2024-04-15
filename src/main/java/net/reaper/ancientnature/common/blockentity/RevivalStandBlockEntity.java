package net.reaper.ancientnature.common.blockentity;

import com.sun.jna.platform.win32.WinDef;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.block.RevivalStand;
import net.reaper.ancientnature.common.config.AncientNatureConfig;
import net.reaper.ancientnature.common.menu.RevivalStandMenu;
import net.reaper.ancientnature.common.menu.UtilMenu;
import net.reaper.ancientnature.common.recipe.RevivalStandRecipe;
import net.reaper.ancientnature.common.util.BurnTimeUtils;
import net.reaper.ancientnature.core.init.ModBlockEntities;
import net.reaper.ancientnature.core.init.ModRecipes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevivalStandBlockEntity extends BlockEntity implements Container, MenuProvider {

    protected List<ItemStack> fossilsUsed = new ArrayList<>();
    protected final ItemStackHandler inv = this.createInventory();
    protected int fuel, itemFuel, amberProgress, maxAmberProgress = 1, fossilProgress, maxFossilProgress = 1;

    public ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> fuel;
                case 1 -> amberProgress;
                case 2 -> maxAmberProgress;
                case 3 -> fossilProgress;
                case 4 -> maxFossilProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> fuel = pValue;
                case 1 -> amberProgress = pValue;
                case 2 -> maxAmberProgress = pValue;
                case 3 -> fossilProgress = pValue;
                case 4 -> maxFossilProgress = pValue;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    };

    public RevivalStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REVIVAL_STAND.get(), pPos, pBlockState);
    }

    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(6) {

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                if (slot > 2)
                    return 1;
                return super.getStackLimit(slot, stack);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (slot == 0) {
                    return BurnTimeUtils.getBurnTime(stack, ModRecipes.REVIVAL_STAND_RECIPE.get()) > 0;
                }
                return super.isItemValid(slot, stack);
            }
        };
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RevivalStandBlockEntity te) {
        te.fuelTick();
        Optional<RevivalStandRecipe> potentialRecipe = te.getMatchingRecipe();
        if (potentialRecipe.isPresent() && te.isHeated()) {
            RevivalStandRecipe recipe = potentialRecipe.get();
            if (te.amberProgress == 0) {
                te.startProcessing(recipe);
                AncientNature.LOGGER.debug(recipe.getAmberInfusionTime() + "|" + recipe.getFossilInfusionTime());
                te.process(recipe);
            } else if (te.fossilProgress < te.maxFossilProgress) {
                te.process(recipe);
            } else {
                te.finishProcessing(recipe);
                te.reset();
            }
        } else {
            te.reset();
        }
    }

    protected void fuelTick() {
        this.fuel = Math.max(0, fuel - 1);
        int maxFuelAddition = Math.min(this.getMaxHeatingPerTick(), this.getMaxFuel() - this.fuel);
        if (this.itemFuel < maxFuelAddition && !this.getItem(0).isEmpty()) {
            this.itemFuel += BurnTimeUtils.getBurnTime(this.getItem(0), ModRecipes.REVIVAL_STAND_RECIPE.get());
            this.getItem(0).shrink(1);
        }
        maxFuelAddition = Math.min(maxFuelAddition, itemFuel);
        itemFuel -= maxFuelAddition;
        this.fuel += maxFuelAddition;
        if (this.isHeated() != this.getBlockState().getValue(RevivalStand.ACTIVE)) {
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(RevivalStand.ACTIVE, this.isHeated()), 3);
        }
    }

    protected void startProcessing(RevivalStandRecipe recipe) {
        this.maxFossilProgress = recipe.getFossilInfusionTime();
        this.maxAmberProgress = recipe.getAmberInfusionTime();
    }

    protected void process(RevivalStandRecipe recipe) {
        if (this.amberProgress < this.maxAmberProgress)
            this.amberProgress++;
        else {
            this.fossilProgress++;
        }
        this.fuel -= 3;
    }

    protected void finishProcessing(RevivalStandRecipe recipe) {
        ItemStack eggs = recipe.assemble(this, this.level.registryAccess());
        for (int i = 0; i < eggs.getCount(); i++) {
            ItemStack eggCopy = eggs.copy();
            eggCopy.setCount(1);
            this.setItem(3 + i, eggCopy);
        }
    }

    protected void reset() {
        this.amberProgress = 0;
        this.fossilProgress = 0;
    }

    /**
     * @return a matching recipe that matches as described in {@link net.minecraft.world.item.crafting.Recipe#matches(Container, Level)}
     */
    public Optional<RevivalStandRecipe> getMatchingRecipe() {
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.REVIVAL_STAND_RECIPE.get(), this, this.level);
    }

    protected boolean isHeated() {
        return this.fuel == getMaxFuel();
    }

    public int getMaxFuel() {
        return AncientNatureConfig.MAX_FUEL.get();
    }

    public int getMaxHeatingPerTick() {
        return AncientNatureConfig.MAX_HEATING.get();
    }

    public void shrinkAndAddOneFossil(ItemStack fossil) {
        ItemStack copy = fossil.copy();
        copy.setCount(1);
        fossil.shrink(1);
        this.addUsedFossil(copy);
    }

    public void addUsedFossil(ItemStack fossil) {
        for (ItemStack existingFossil : this.fossilsUsed) {
            if (existingFossil.getItem() == fossil.getItem()) {
                existingFossil.grow(fossil.getCount());
                return;
            }
        }
        this.fossilsUsed.add(fossil);
    }

    public List<ItemStack> getFossilsUsed() {
        return fossilsUsed;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", this.inv.serializeNBT());
        pTag.putInt("fuel", this.fuel);
        pTag.putInt("itemFuel", this.itemFuel);
        pTag.putInt("amberProgress", this.amberProgress);
        pTag.putInt("maxAmberProgress", this.maxAmberProgress);
        pTag.putInt("fossilProgress", this.fossilProgress);
        pTag.putInt("maxFossilProgress", this.maxAmberProgress);
        for (int i = 0; i < this.fossilsUsed.size(); i++) {
            pTag.put("usedFossil" + i, this.fossilsUsed.get(i).serializeNBT());
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.inv.deserializeNBT(pTag.getCompound("inventory"));
        this.fuel = pTag.getInt("fuel");
        this.itemFuel = pTag.getInt("itemFuel");
        this.amberProgress = pTag.getInt("amberProgress");
        this.maxAmberProgress = pTag.getInt("maxAmberProgress");
        this.fossilProgress = pTag.getInt("fossilProgress");
        this.maxFossilProgress = pTag.getInt("maxFossilProgress");
        for (int i = 0; pTag.contains("usedFossil" + i); i++) {
            this.fossilsUsed.add(ItemStack.of(pTag.getCompound("usedFossil" + i)));
        }
    }

    @Override
    public int getContainerSize() {
        return this.inv.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            if (!this.getItem(i).isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return this.inv.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return this.inv.extractItem(pSlot, pAmount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return this.inv.extractItem(pSlot, Integer.MAX_VALUE, false);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        this.inv.setStackInSlot(pSlot, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            this.inv.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public Component getDisplayName() {
        return UtilMenu.makeTranslationKey("revival_stand");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RevivalStandMenu(pContainerId, pPlayerInventory, this);
    }

    public int getFuel() {
        return fuel;
    }

    public int getAmberProgress() {
        return amberProgress;
    }

    public int getMaxAmberProgress() {
        return maxAmberProgress;
    }

    public int getFossilProgress() {
        return fossilProgress;
    }

    public int getMaxFossilProgress() {
        return maxFossilProgress;
    }
}
