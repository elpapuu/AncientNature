package net.reaper.ancientnature.core.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.blockentity.RevivalStandBlockEntity;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AncientNature.MOD_ID);


    public static final RegistryObject<BlockEntityType<RevivalStandBlockEntity>> REVIVAL_STAND = TES.register("revival_stand", () -> BlockEntityType.Builder.of(RevivalStandBlockEntity::new, ModBlocks.REVIVAL_STAND.get()).build(null));

}
