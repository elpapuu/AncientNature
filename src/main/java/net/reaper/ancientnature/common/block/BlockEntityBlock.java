package net.reaper.ancientnature.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BlockEntityBlock extends Block implements EntityBlock {

    protected final BiFunction<BlockPos, BlockState, BlockEntity> blockEntityCreator;

    public BlockEntityBlock(BiFunction<BlockPos, BlockState, BlockEntity> blockEntityCreator, Properties pProperties) {
        super(pProperties);
        this.blockEntityCreator = blockEntityCreator;
    }

    public BlockEntityBlock(Supplier<BlockEntityType<?>> supplier, Properties pProperties) {
       this((pos, state) -> supplier.get().create(pos, state), pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return this.blockEntityCreator.apply(pPos, pState);
    }

    @javax.annotation.Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
        return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
    }
}
