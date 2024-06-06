package net.reaper.ancientnature.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.reaper.ancientnature.common.blockentity.RevivalStandBlockEntity;
import net.reaper.ancientnature.common.util.WorldUtils;
import net.reaper.ancientnature.core.init.ModBlockEntities;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModParticles;
import net.reaper.ancientnature.core.init.ModTags;
import org.jetbrains.annotations.Nullable;

public class RevivalStand extends BlockEntityBlock {

    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 1, 4);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    protected static final VoxelShape SHAPE = Shapes.or(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D), Block.box(8.0D, 0.0D, 7.0D, 10.0D, 10.0D, 9.0D));

    public RevivalStand() {
        super(RevivalStandBlockEntity::new, Properties.copy(Blocks.BREWING_STAND).strength(1.5f).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false).setValue(STAGE, 1));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pState.getValue(STAGE) == 4) {
            if (!pLevel.isClientSide) {
                RevivalStandBlockEntity te = WorldUtils.getTileEntity(RevivalStandBlockEntity.class, pLevel, pPos);
                if (te != null){
                    NetworkHooks.openScreen((ServerPlayer) pPlayer, te, pPos);
                }
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        if (pPlayer.getItemInHand(pHand).is(ModTags.Items.FOSSILS) && pState.getValue(STAGE) < 4) {
            if (!pLevel.isClientSide) {
                pLevel.setBlock(pPos, pState.setValue(STAGE, pState.getValue(STAGE) + 1), 3);
                RevivalStandBlockEntity te = WorldUtils.getTileEntity(RevivalStandBlockEntity.class, pLevel, pPos);
                if (te != null) {
                    ItemStack copy = pPlayer.getItemInHand(pHand).copy();
                    copy.setCount(1);
                    te.addUsedFossil(copy);
                }
                if (!pPlayer.isCreative())
                    pPlayer.getItemInHand(pHand).shrink(1);
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(ACTIVE)) {
            return Shapes.or(SHAPE, Block.box(8.0D, 0.0D, 7.0D, 10.0D, 15.0D, 9.0D));
        }
        return SHAPE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            RevivalStandBlockEntity te = WorldUtils.getTileEntity(RevivalStandBlockEntity.class, pLevel, pPos);
            if (te != null) {
                if (pState.getValue(STAGE) != 4) {
                    Containers.dropContents(pLevel, pPos, new SimpleContainer(te.getFossilsUsed().toArray(new ItemStack[0])));
                    Containers.dropContents(pLevel, pPos, new SimpleContainer(new ItemStack(Blocks.BREWING_STAND)));
                } else {

                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(ACTIVE) && pLevel.getBlockState(pPos.above()).isAir()) {
            for (int i = 0; i < 3; i++) {
                double dx = Mth.nextDouble(pRandom, -.02d, .02d);
                double dz = Mth.nextDouble(pRandom, -.02d, .02d);
                pLevel.addParticle(ModParticles.REVIVAL_STAND_PARTICLE.get(), pPos.getX() + .5d, pPos.getY() + 1d, pPos.getZ() + 0.5d, dx, 0.04D, dz);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(STAGE, ACTIVE);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.REVIVAL_STAND.get(), RevivalStandBlockEntity::serverTick);
    }
}
