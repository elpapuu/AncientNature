package net.reaper.ancientnature.common.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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
                event.getEntity().startUsingItem(InteractionHand.MAIN_HAND);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public static void onUseStart(LivingEntityUseItemEvent.Start event) {
        if (event.getItem().getItem() == Items.BRUSH) {
            if (event.getEntity().getOffhandItem().getItem() == ModItems.CAMBRIAN_FOSSIL.get()) { // Check if holding fossil in offhand
                event.setDuration(12 * 20);
            }
        }
    }

    @SubscribeEvent
    public static void onUseTick(LivingEntityUseItemEvent.Stop event) {
        if (event.getItem().getItem() == Items.BRUSH) {
            if (event.getEntity().getOffhandItem().getItem() == ModItems.CAMBRIAN_FOSSIL.get()) { // Check if holding fossil in offhand
                System.out.println(event.getDuration());
                if (event.getDuration() > 0) {
                    event.setCanceled(true);
                    return;
                }
                if (event.getEntity().level().random.nextFloat() < 0.15) { // 15% chance
                    if (event.getEntity() instanceof Player player)
                        player.addItem(ModItems.ANOMALOCARIS_FOSSIL.get().getDefaultInstance());
                }
                event.getEntity().getOffhandItem().shrink(1);
                event.getItem().hurtAndBreak(1, event.getEntity(), p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
    }
}
