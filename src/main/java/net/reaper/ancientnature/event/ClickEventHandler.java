package net.reaper.ancientnature.event;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.item.ModItems;
import net.reaper.ancientnature.sound.ModSounds;


@Mod.EventBusSubscriber(modid = "ancientnature", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClickEventHandler {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.MAIN_HAND) { // Check if main hand
            if (event.getItemStack().getItem() == Items.BRUSH) { // Check if holding brush
                if (event.getEntity().getOffhandItem().getItem() == ModItems.CAMBRIAN_FOSSIL.get())
                    event.getEntity().playSound(null,0 ,0 ,0 ,ModSounds.CLEANED_FOSSIL.get(), SoundSource.PLAYERS, 1, 1); { // Check if holding fossil in offhand
                    if (Math.random() < 0.15) { // 15% chance
                        event.getEntity().addItem(ModItems.ANOMALOCARIS_FOSSIL.get().getDefaultInstance()); // Give another fossil


                    } else {
                        event.getEntity().getOffhandItem().setCount(event.getEntity().getOffhandItem().getCount() - 1); // Decrease fossil count
                    }
                }
            }
        }
    }
}
