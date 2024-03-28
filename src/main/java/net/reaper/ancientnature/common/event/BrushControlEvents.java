package net.reaper.ancientnature.common.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModItems;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BrushControlEvents {

    @SubscribeEvent
    public static void rightCLick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.MAIN_HAND && event.getItemStack().getItem() == Items.BRUSH) {
            //TODO make this check with reipces
            if (event.getEntity().getOffhandItem().getItem() == ModItems.CAMBRIAN_FOSSIL.get()) { // Check if holding fossil in offhand
                event.getItemStack().hurtAndBreak(1, event.getEntity(), p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                event.getEntity().getOffhandItem().shrink(1);
                event.getEntity().addItem(ModItems.ANOMALOCARIS_FOSSIL.get().getDefaultInstance());
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
