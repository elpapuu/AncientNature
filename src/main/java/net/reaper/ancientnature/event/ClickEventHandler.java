package net.reaper.ancientnature.event;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.item.ModItems;

@Mod.EventBusSubscriber(modid = "tu_mod_id", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClickEventHandler {

    public static void onPlayerLeftClick(PlayerInteractEvent.RightClickItem event) {
        // Verificar si el jugador hace clic izquierdo con un objeto en la mano principal
        ItemStack heldItem = event.getPlayer().getHeldItemMainhand();

        // Verificar si el jugador tiene el objeto de la brocha en la mano principal
        if (!heldItem.isEmpty() && heldItem.getItem() == Items.BRUSH) {
            // Aquí puedes implementar la lógica para dar otro objeto o hacer que el objeto de la brocha desaparezca
            if (Math.random() < 0.5) { // 50% de probabilidad
                // Dar otro objeto al jugador
                event.getPlayer().addItemStackToInventory(new ItemStack(ModItems.ANOMALOCARIS_FOSSIL.get()));
            } else {
                // Hacer que el objeto de la brocha desaparezca
                event.getPlayer().setHeldItem(event.getHand(), ItemStack.EMPTY);
            }
        }
    }
}



