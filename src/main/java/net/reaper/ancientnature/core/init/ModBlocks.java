package net.reaper.ancientnature.core.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.reaper.ancientnature.AncientNature;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    public static final RegistryObject<Block> FOSSILIZED_GRAVEL = registryBlock("fossilized_gravel", () -> new Block(BlockBehaviour.Properties.of().strength(1)));

    public static final RegistryObject<BrushableBlock> SUSPICIOUS_FOSSILIZED_GRAVEL = registryBlock("suspicious_fossilized_gravel", () -> new BrushableBlock(ModBlocks.FOSSILIZED_GRAVEL.get(), BlockBehaviour.Properties.of().strength(1), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED));




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

