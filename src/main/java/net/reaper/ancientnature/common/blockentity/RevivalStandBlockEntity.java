package net.reaper.ancientnature.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.reaper.ancientnature.core.init.ModBlockEntities;

import java.util.ArrayList;
import java.util.List;

public class RevivalStandBlockEntity extends BlockEntity {

    protected List<ItemStack> fossilsUsed = new ArrayList<>();

    public RevivalStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REVIVAL_STAND.get(), pPos, pBlockState);
    }

    public void shrinkAndAddOneFossil(ItemStack fossil){
        ItemStack copy = fossil.copy();
        copy.setCount(1);
        fossil.shrink(1);
        this.addUsedFossil(copy);
    }

    public void addUsedFossil(ItemStack fossil){
        for (ItemStack existingFossil : this.fossilsUsed){
            if (existingFossil.getItem() == fossil.getItem()){
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
        for (int i = 0; i < this.fossilsUsed.size(); i++) {
            pTag.put("usedFossil" + i, this.fossilsUsed.get(i).serializeNBT());
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        for (int i = 0; pTag.contains("usedFossil" + i); i++) {
            this.fossilsUsed.add(ItemStack.of(pTag.getCompound("usedFossil" + i)));
        }
    }
}
