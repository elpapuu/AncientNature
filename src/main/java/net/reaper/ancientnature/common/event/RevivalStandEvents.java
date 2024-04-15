package net.reaper.ancientnature.common.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.blockentity.RevivalStandBlockEntity;
import net.reaper.ancientnature.common.config.AncientNatureConfig;
import net.reaper.ancientnature.common.util.WorldUtils;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModRecipes;
import net.reaper.ancientnature.core.init.ModTags;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = AncientNature.MOD_ID)
public class RevivalStandEvents {

    @SubscribeEvent
    public static void handleRightClick(PlayerInteractEvent.RightClickBlock event){
        BlockState hit = event.getEntity().level().getBlockState(event.getHitVec().getBlockPos());
        if (hit.getBlock() == Blocks.BREWING_STAND && event.getItemStack().is(ModTags.Items.FOSSILS)){
            if (!event.getLevel().isClientSide) {
                event.getEntity().level().setBlock(event.getHitVec().getBlockPos(), ModBlocks.REVIVAL_STAND.get().defaultBlockState(), 3);
                RevivalStandBlockEntity te = WorldUtils.getTileEntity(RevivalStandBlockEntity.class, event.getLevel(), event.getHitVec().getBlockPos());
                if (te != null) {
                    ItemStack copy = event.getEntity().getItemInHand(event.getHand()).copy();
                    copy.setCount(1);
                    te.addUsedFossil(copy);
                }
                if (!event.getEntity().isCreative()) {
                    event.getItemStack().shrink(1);
                }
            }
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide));
        }
    }

    @SubscribeEvent
    public static void addBlazePowderBurn(FurnaceFuelBurnTimeEvent event){
        if (event.getRecipeType() == ModRecipes.REVIVAL_STAND_RECIPE.get()){
            if (event.getItemStack().getItem() == Items.BLAZE_POWDER){
                event.setBurnTime(AncientNatureConfig.BLAZE_POWDER_BURN_TIME.get());
            }
        }
    }
}
