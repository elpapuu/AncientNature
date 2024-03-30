package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModBlocks;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AncientNature.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.FOSSILIZED_GRAVEL.get());
        simpleBlock(ModBlocks.DEEPSLATE_AMBER.get());
        simpleBlock(ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
        makeFossil(ModBlocks.SUSPICIOUS_FOSSILIZED_GRAVEL.get());
    }

    protected void makeFossil(BrushableBlock block){
        getVariantBuilder(block).forAllStates(state -> {
            int dusted = state.getValue(BlockStateProperties.DUSTED);
            dusted++;
            ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
            String name = registryName.getPath();
            name += "_" + dusted;
            ConfiguredModel.Builder<?> model = ConfiguredModel.builder().modelFile(models().cubeAll(name, modLoc("block/" + name)));
            if (dusted == 1){
                this.simpleBlockItem(block, models().cubeAll(name, modLoc("block/" + name)));
            }
            return model.build();
        });
    }

    @Override
    public void simpleBlock(Block block, ModelFile model) {
        super.simpleBlock(block, model);
        simpleBlockItem(block, model);
    }
}
