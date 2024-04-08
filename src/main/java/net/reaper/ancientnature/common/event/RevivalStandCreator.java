package net.reaper.ancientnature.common.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModTags;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = AncientNature.MOD_ID)
public class RevivalStandCreator {

    @SubscribeEvent
    public static void handleRightClick(PlayerInteractEvent.RightClickBlock event){
        BlockState hit = event.getEntity().level().getBlockState(event.getHitVec().getBlockPos());
        if (hit.getBlock() == Blocks.BREWING_STAND && event.getItemStack().is(ModTags.Items.FOSSILS)){
            event.getEntity().level().setBlock(event.getHitVec().getBlockPos(), ModBlocks.REVIVAL_STAND.get().defaultBlockState(), 3);
            if (!event.getEntity().isCreative()){
                event.getItemStack().shrink(1);
            }
            event.setUseBlock(Event.Result.DENY);
            event.setUseItem(Event.Result.DENY);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
