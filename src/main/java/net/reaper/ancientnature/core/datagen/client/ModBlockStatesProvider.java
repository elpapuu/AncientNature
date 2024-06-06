package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.block.RevivalStand;
import net.reaper.ancientnature.common.util.ResourceLocationUtils;
import net.reaper.ancientnature.core.init.ModBlocks;

import javax.sound.sampled.ReverbType;
import java.util.function.Function;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AncientNature.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.DEEPSLATE_AMBER.get());
        simpleBlock(ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
        simpleBlock(ModBlocks.DEEPSLATE_CARBONIFEROUS.get());
        simpleBlock(ModBlocks.DEEPSLATE_DEVONIAN_FOSSIL.get());
        simpleBlock(ModBlocks.STONE_PERMIAN_FOSSIL.get());
        makeFossil(ModBlocks.MUD_WITH_FOSSILS.get());
        revivalStand(ModBlocks.REVIVAL_STAND.get());
        roeBlock(ModBlocks.ARANDASPIS_ROE.get(), false);
    }

    protected void revivalStand(Block block){
        getVariantBuilder(block).forAllStates(state -> {
            ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
            String name = registryName.getPath();
            int stage = state.getValue(RevivalStand.STAGE);
            if (stage < 4){
                name += "_stage" + stage;
            }else {
                boolean active = state.getValue(RevivalStand.ACTIVE);
                name += active ? "_active" : "_unactive";
            }
            /*
            if (stage == 4 && !state.getValue(RevivalStand.ACTIVE)){
                simpleBlockItem(block, models().getBuilder(name).parent(models().getExistingFile(AncientNature.modLoc("block/revival_stand_prefab"))).texture("texture", AncientNature.modLoc("block/" + name)).renderType(mcLoc("cutout")));
            }
             */
            ModelBuilder<?> modelBuilder = models().getBuilder(name).parent(models().getExistingFile(AncientNature.modLoc("block/revival_stand_prefab"))).texture("texture", AncientNature.modLoc("block/" + name));
            if (state.getValue(RevivalStand.ACTIVE)){
                modelBuilder = modelBuilder.renderType(mcLoc("translucent"));
            }else {
                modelBuilder = modelBuilder.renderType(mcLoc("cutout"));
            }
            return ConfiguredModel.builder().modelFile(modelBuilder).build();
        });
    }

    protected void makeFossil(BrushableBlock block){
        getVariantBuilder(block).forAllStates(state -> {
            int dusted = state.getValue(BlockStateProperties.DUSTED);
            ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
            String name = registryName.getPath();
            if (dusted > 0)
                name += "_" + dusted;
            ConfiguredModel.Builder<?> model = ConfiguredModel.builder().modelFile(models().cubeAll(name, modLoc("block/" + name)));
            if (dusted <= 0){
                this.simpleBlockItem(block, models().cubeAll(name, modLoc("block/" + name)));
            }
            return model.build();
        });
    }

    protected void roeBlock(Block block){
        roeBlock(block, true);
    }

    protected void roeBlock(Block block, boolean generateBlockItem){
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);
        ModelFile model = models().withExistingParent(name.toString(), modLoc("block/roe_prefab")).texture("roe", ResourceLocationUtils.prepend(name, "block/")).renderType(mcLoc("cutout"));
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationX(dir == Direction.DOWN ? 0 : dir.getAxis().isHorizontal() ? 90 : 180)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot())) % 360)
                            .build();
                });
        if (generateBlockItem)
            simpleBlockItem(block, model);
    }

    @Override
    public void simpleBlock(Block block, ModelFile model) {
        super.simpleBlock(block, model);
        simpleBlockItem(block, model);
    }
}
