package net.reaper.ancientnature.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.item.ModItems;
import net.reaper.ancientnature.sound.ModSounds;

import static net.reaper.ancientnature.sound.ModSounds.CLEANED_FOSSIL;


@Mod.EventBusSubscriber(modid = "ancientnature", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClickEventHandler {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.MAIN_HAND) { // Check if main hand
            if (event.getItemStack().getItem() == Items.BRUSH) { // Check if holding brush
                if (event.getEntity().getOffhandItem().getItem() == ModItems.CAMBRIAN_FOSSIL.get()) { // Check if holding fossil in offhand
                    if (Math.random() < 0.4) { // 15% chance
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
