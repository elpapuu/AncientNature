package net.reaper.ancientnature.event;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.item.ModItems;

@Mod.EventBusSubscriber(modid = "ancientnature", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClickEventHandler {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.MAIN_HAND) { // Check if main hand
            if (event.getItemStack().getItem() == Items.BRUSH) { // Check if holding brush
                if (event.getEntity().getOffhandItem().getItem() == ModItems.CAMBRIAN_FOSSIL.get()) { // Check if holding fossil in offhand
                    if (Math.random() < 0.3) { // 30% chance
                        event.getEntity().addItem(ModItems.ANOMALOCARIS_FOSSIL.get().getDefaultInstance()); // Give another fossil
                        event.getEntity().playSound(null, 0, 0, 0, SoundEvent.("cleaned_fossil"), SoundSource.PLAYERS, 1, 1);
                    } else {
                        event.getEntity().getOffhandItem().setCount(event.getEntity().getOffhandItem().getCount() - 1); // Decrease fossil count
                    }
                }
            }
        }
    }
}
