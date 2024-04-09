package net.reaper.ancientnature.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.reaper.ancientnature.common.block.RevivalStand;
import org.jetbrains.annotations.Nullable;

public class RevivalStandItem extends BlockItem {
    public RevivalStandItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext pContext) {
        BlockState state = super.getPlacementState(pContext);
        if (state == null)
            return null;
        return state.setValue(RevivalStand.STAGE, 4);
    }
}
