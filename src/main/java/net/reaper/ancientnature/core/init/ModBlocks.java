package net.reaper.ancientnature.core.init;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.reaper.ancientnature.AncientNature;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AncientNature.MOD_ID);

    public static final RegistryObject<Block> DEEPSLATE_AMBER = registryBlock("deepslate_amber",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> DEEPSLATE_CAMBRIAN_FOSSIL = registryBlock("deepslate_cambrian_fossil",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<FallingBlock> FOSSILIZED_GRAVEL = registryBlock("fossilized_gravel", () -> new FallingBlock(BlockBehaviour.Properties.of().strength(1)));

    public static final RegistryObject<BrushableBlock> SUSPICIOUS_FOSSILIZED_GRAVEL = registryBlock("suspicious_fossilized_gravel", () -> new BrushableBlock(ModBlocks.FOSSILIZED_GRAVEL.get(), BlockBehaviour.Properties.of().strength(1), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED){
        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
            BrushableBlockEntity te = new BrushableBlockEntity(pPos, pState);
            Random random = new Random();
            te.setLootTable(ModLootTables.SUSPICIOUS_GRAVEL_BRUSH, random.nextLong());
            return te;
        }
    });




    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


        public static void register(IEventBus eventBus) {
            BLOCKS.register(eventBus);
        }
    }

