package net.reaper.ancientnature.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class RoeBlock extends Block implements SimpleWaterloggedBlock {
    protected final int minHatchTicks, maxHatchTicks, minEntities, maxEntities;
    protected final Supplier<EntityType<?>> entitySupplier;

    /**
     * @param pProperties
     * @param minHatchTicks  the min amount of ticks this will need to hatch (inclusvie)
     * @param maxHatchTicks  the max amount of ticks this will need to hatch (exclusvie)
     * @param minEntities    the min amount of entities that might come out (inclusvie)
     * @param maxEntities    the max amount of entities that can come out (inclusvie)
     * @param entitySupplier the entity that will spawn, note that the supplier is required otherwise this will throw errors
     */
    public RoeBlock(Properties pProperties, int minHatchTicks, int maxHatchTicks, int minEntities, int maxEntities, Supplier<EntityType<?>> entitySupplier) {
        super(pProperties);
        this.minHatchTicks = minHatchTicks;
        this.maxHatchTicks = maxHatchTicks;
        this.minEntities = minEntities;
        this.maxEntities = maxEntities;
        this.entitySupplier = entitySupplier;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction dir = pState.getValue(BlockStateProperties.FACING);
        return switch (dir) {
            case DOWN -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
            case UP -> Block.box(0d, 14.5d, 0d, 16d, 16d, 16d);
            case NORTH -> Block.box(0, 0, 0, 16, 16, 1.5d);
            case EAST -> Block.box(14.5d, 0d, 0d, 16d, 16d, 16d);
            case SOUTH -> Block.box(0d, 0d, 14.5d, 16d, 16d, 16d);
            default -> Block.box(0, 0, 0, 1.5d, 16d, 16d);

        };
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        FluidState fluidstate = pLevel.getFluidState(pPos);
        return fluidstate.getType() == Fluids.WATER && pLevel.getBlockState(pPos.relative(pState.getValue(BlockStateProperties.FACING))).isFaceSturdy(pLevel, pPos.relative(pState.getValue(BlockStateProperties.FACING)), pState.getValue(BlockStateProperties.FACING).getOpposite());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidState = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, pContext.getClickedFace().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, getEntitySpawnHatchDelay(pLevel.getRandom()));
    }

    protected int getEntitySpawnHatchDelay(RandomSource pRandom) {
        return pRandom.nextInt(this.minHatchTicks, this.maxHatchTicks);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return !this.canSurvive(pState, pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!this.canSurvive(pState, pLevel, pPos)) {
            this.destroyBlock(pLevel, pPos);
        } else {
            this.hatchFrogspawn(pLevel, pPos, pRandom);
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.getType().equals(EntityType.FALLING_BLOCK)) {
            this.destroyBlock(pLevel, pPos);
        }

    }

    protected void hatchFrogspawn(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.destroyBlock(pLevel, pPos);
        pLevel.playSound((Player) null, pPos, SoundEvents.FROGSPAWN_HATCH, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.spawnTadpoles(pLevel, pPos, pRandom);
    }

    protected void destroyBlock(Level pLevel, BlockPos pPos) {
        pLevel.destroyBlock(pPos, false);
    }

    protected void spawnTadpoles(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int i = pRandom.nextInt(2, 6);

        for (int j = 1; j <= i; ++j) {
            Entity tadpole = this.entitySupplier.get().create(pLevel);
            if (tadpole != null) {
                double d0 = (double) pPos.getX() + this.getRandomTadpolePositionOffset(pRandom);
                double d1 = (double) pPos.getZ() + this.getRandomTadpolePositionOffset(pRandom);
                int k = pRandom.nextInt(1, 361);
                tadpole.moveTo(d0, (double) pPos.getY() - 0.5D, d1, (float) k, 0.0F);
                if (tadpole instanceof Mob living) {
                    living.setPersistenceRequired();
                }
                if (tadpole instanceof AgeableMob ageable) {
                    ageable.setAge(-24000);
                }

                pLevel.addFreshEntity(tadpole);
            }
        }

    }

    protected double getRandomTadpolePositionOffset(RandomSource pRandom) {
        double d0 = (double) (Tadpole.HITBOX_WIDTH / 2.0F);
        return Mth.clamp(pRandom.nextDouble(), d0, 1.0D - d0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BlockStateProperties.FACING, BlockStateProperties.WATERLOGGED);
    }
}
