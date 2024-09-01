package net.reaper.ancientnature.core.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.reaper.ancientnature.AncientNature;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.common.block.FourEggBlock;
import net.reaper.ancientnature.common.block.MudFossilBlock;
import net.reaper.ancientnature.common.block.RevivalStand;
import net.reaper.ancientnature.common.block.RoeBlock;
import net.reaper.ancientnature.common.item.RevivalStandItem;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AncientNature.MOD_ID);

    public static final RegistryObject<Block> DEEPSLATE_AMBER = registryBlock("deepslate_amber",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> STONE_AMBER = registryBlock("stone_amber",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_CAMBRIAN_FOSSIL = makeDeepslateFossilBlock("deepslate_cambrian_fossil");
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_CARBONIFEROUS = makeDeepslateFossilBlock("deepslate_carboniferous");
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_DEVONIAN_FOSSIL = makeDeepslateFossilBlock("deepslate_devonian_fossil");
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_PERMIAN_FOSSIL = makeDeepslateFossilBlock("deepslate_permian_fossil_ore");
    public static final RegistryObject<DropExperienceBlock> CRETACEOUS_FOSSILS = registryBlock("cretaceous_fossils", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops(), ConstantInt.of(1)));
    public static final RegistryObject<DropExperienceBlock> QUATERNARY_FOSSILS = registryBlock("quaternary_fossils_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops(), ConstantInt.of(1)));

    public static final RegistryObject<DropExperienceBlock> STONE_PERMIAN_FOSSIL = registryBlock("stone_permian_fossil_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops(), ConstantInt.of(1)));
    public static final RegistryObject<BrushableBlock> MUD_WITH_FOSSILS = registryBlock("mud_with_fossils", () -> new MudFossilBlock(Blocks.MUD, BlockBehaviour.Properties.copy(Blocks.MUD), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, ModLootTables.MUD_FOSSIL_BRUSH));
    public static final RegistryObject<RevivalStand> REVIVAL_STAND = register("revival_stand", RevivalStand::new, b -> new RevivalStandItem(b, new Item.Properties()));

    public static final RegistryObject<RoeBlock> ARANDASPIS_ROE = register("arandaspis_roe", () -> new RoeBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN), 3600, 12000, 1, 4, ModEntities.ARANDASPIS::get), () -> new Item.Properties().stacksTo(16));
    public static final RegistryObject<RoeBlock> ANOMALOCARIS_EGGS = register("anomalocaris_eggs", () -> new RoeBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN), 3600, 12000, 1, 4, ModEntities.ANOMALOCARIS::get), () -> new Item.Properties().stacksTo(16));

    public static final RegistryObject<FourEggBlock> DODO_EGG = register("dodo_egg", () -> new FourEggBlock(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG), ModEntities.DODO), () -> new Item.Properties().stacksTo(16));

    public static RegistryObject<DropExperienceBlock> makeDeepslateFossilBlock(String name){
        return registryBlock(name, () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).strength(5f).requiresCorrectToolForDrops(), ConstantInt.of(1)));
    }


    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties) {
        return register(name, blockSupplier, b -> new BlockItem(b, properties.get()));
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }

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

