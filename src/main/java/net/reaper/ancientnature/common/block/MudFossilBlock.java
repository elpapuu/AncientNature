package net.reaper.ancientnature.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.reaper.ancientnature.core.init.ModLootTables;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MudFossilBlock extends BrushableBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    protected final ResourceLocation lootTable;
    public MudFossilBlock(Block pTurnsInto, Properties pProperties, SoundEvent pBrushSound, SoundEvent pBrushCompletedSound) {
        this(pTurnsInto, pProperties, pBrushSound, pBrushCompletedSound, null);
    }

    public MudFossilBlock(Block pTurnsInto, Properties pProperties, SoundEvent pBrushSound, SoundEvent pBrushCompletedSound, ResourceLocation lootTable) {
        super(pTurnsInto, pProperties, pBrushSound, pBrushCompletedSound);
        this.lootTable = lootTable;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public VoxelShape getBlockSupportShape(BlockState p_221566_, BlockGetter p_221567_, BlockPos p_221568_) {
        return Shapes.block();
    }

    public VoxelShape getVisualShape(BlockState p_221556_, BlockGetter p_221557_, BlockPos p_221558_, CollisionContext p_221559_) {
        return Shapes.block();
    }

    public boolean isPathfindable(BlockState p_221547_, BlockGetter p_221548_, BlockPos p_221549_, PathComputationType p_221550_) {
        return false;
    }

    public float getShadeBrightness(BlockState p_221552_, BlockGetter p_221553_, BlockPos p_221554_) {
        return 0.2F;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        BrushableBlockEntity te = new BrushableBlockEntity(pPos, pState);
        if (this.lootTable != null) {
            Random random = new Random();
            te.setLootTable(this.lootTable, random.nextLong());
        }
        return te;
    }
}
