package net.reaper.ancientnature.event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.item.ModItems;

@Mod.EventBusSubscriber(modid = "ancientnature", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClickEventHandler {

    public static void onPlayerLeftClick(PlayerInteractEvent.RightClickItem event) {

        ItemStack mainHandItem;
        mainHandItem = event.getPlayer().getHeldItemMainhand();
        ItemStack offHandItem;
        offHandItem = event.getPlayer().getHeldItemOffhand();

        if (!mainHandItem.isEmpty() && mainHandItem.getItem().getDescriptionId().equals("minecraft:brush") &&
                !offHandItem.isEmpty() && offHandItem.getItem() == ModItems.CAMBRIAN_FOSSIL.get()) {

            if (Math.random() < 0.3) {

                event.getPlayer().addItemStackToInventory(new ItemStack(ModItems.ANOMALOCARIS_FOSSIL.get()));
            } else {
                event.getPlayer().setHeldItem(event.getHand(), ItemStack.EMPTY);
            }
        }
    }
}



