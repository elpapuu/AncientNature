package net.reaper.ancientnature.event;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.core.init.ModItems;


@Mod.EventBusSubscriber(modid = "ancientnature", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClickEventHandler {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.MAIN_HAND) { // Check if main hand
            if (event.getItemStack().getItem() == Items.BRUSH) { // Check if holding brush
                if (event.getEntity().getOffhandItem().getItem() == ModItems.CAMBRIAN_FOSSIL.get()) { // Check if holding fossil in offhand
                    //dont create new random objects as this is very slow especially when this event is executed every tick
                    if (event.getEntity().level().random.nextFloat() < 0.4) { // 15% chance
                        event.getEntity().addItem(ModItems.ANOMALOCARIS_FOSSIL.get().getDefaultInstance());
                        event.getEntity().getOffhandItem().setCount(event.getEntity().getOffhandItem().getCount() - 1);// Give another fossil
                    } else {
                        event.getEntity().getOffhandItem().setCount(event.getEntity().getOffhandItem().getCount() - 1); // Decrease fossil count
                    }
                    event.getEntity().playSound(SoundEvents.BRUSH_GENERIC, 1.0f, 1.0f);
                    event.getItemStack().hurt(4, RandomSource.create(), null);


                }
            }
        }
    }
}
