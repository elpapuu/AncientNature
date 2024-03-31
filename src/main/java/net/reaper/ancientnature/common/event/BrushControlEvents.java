package net.reaper.ancientnature.common.event;

import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.recipe.BrushingRecipe;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModRecipes;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BrushControlEvents {

    @SubscribeEvent
    public static void rightCLick(PlayerInteractEvent.RightClickItem event) {
        Container hands = new SimpleContainer(event.getEntity().getMainHandItem(), event.getEntity().getOffhandItem());
        List<BrushingRecipe> recipes = new ArrayList<>(event.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.BRUSHING_RECIPE.get()));
        if (!recipes.isEmpty()) {
            recipes.removeIf(r -> r.matches(hands, event.getLevel()));
            for (BrushingRecipe recipe : recipes) {
                ItemStack stack = recipe.assemble(hands, event.getLevel().registryAccess());
                if (!stack.isEmpty()) {
                    addOrDropStack(event.getEntity(), stack);
                }
                if (recipe.getBrush().test(event.getEntity().getOffhandItem())) {
                    event.getEntity().getOffhandItem().hurtAndBreak(1, event.getEntity(), p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                    event.getEntity().getMainHandItem().shrink(1);
                } else {
                    event.getEntity().getMainHandItem().hurtAndBreak(1, event.getEntity(), p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                    event.getEntity().getOffhandItem().shrink(1);
                }

            }
            event.setCancellationResult(InteractionResult.SUCCESS);

        }
    }

    protected static void addOrDropStack(Player player, ItemStack stack) {
        if (!player.addItem(stack)) {
            ItemEntity item = player.drop(stack, false);
            player.level().addFreshEntity(item);
        }
    }
}
