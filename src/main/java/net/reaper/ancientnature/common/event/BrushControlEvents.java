package net.reaper.ancientnature.common.event;

import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.recipe.BrushingRecipe;
import net.reaper.ancientnature.common.recipe.WaterWashingRecipe;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModRecipes;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BrushControlEvents {

    @SubscribeEvent
    public static void rightCLick(PlayerInteractEvent.RightClickItem event) {
        BlockHitResult blockhitresult = getPlayerPOVHitResult(event.getLevel(), event.getEntity(), ClipContext.Fluid.SOURCE_ONLY);
        if ((blockhitresult.getType() == HitResult.Type.BLOCK && event.getLevel().getFluidState(blockhitresult.getBlockPos()).is(Fluids.WATER)) || isWaterBottle(event.getEntity().getOffhandItem()) || isWaterBottle(event.getEntity().getMainHandItem())) {
            if (!handleWaterBrushing(event, blockhitresult.getType() == HitResult.Type.BLOCK && event.getLevel().getFluidState(blockhitresult.getBlockPos()).is(Fluids.WATER)))
                handleBrushing(event);
        } else {
            handleBrushing(event);
        }
    }

    protected static void handleBrushing(PlayerInteractEvent.RightClickItem event) {
        Container hands = new SimpleContainer(event.getEntity().getMainHandItem(), event.getEntity().getOffhandItem());
        List<BrushingRecipe> recipes = new ArrayList<>(event.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.BRUSHING_RECIPE.get()));
        if (!recipes.isEmpty()) {
            recipes.removeIf(r -> !r.matches(hands, event.getLevel()));
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

    public static boolean handleWaterBrushing(PlayerInteractEvent.RightClickItem event, boolean selectingWater) {
        List<WaterWashingRecipe> finalRecipes;
        if (selectingWater) {
            List<WaterWashingRecipe> recipes = new ArrayList<>(event.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.WATER_WASHING.get()));
            recipes.removeIf(r -> !r.matches(new SimpleContainer(event.getEntity().getMainHandItem()), event.getLevel()) && !r.matches(new SimpleContainer(event.getEntity().getOffhandItem()), event.getLevel()));
            finalRecipes = recipes;
        } else {
            if (isWaterBottle(event.getEntity().getOffhandItem())) {
                List<WaterWashingRecipe> recipes = new ArrayList<>(event.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.WATER_WASHING.get()));
                recipes.removeIf(r -> !r.matches(new SimpleContainer(event.getEntity().getMainHandItem()), event.getLevel()));
                finalRecipes = recipes;
            } else {
                List<WaterWashingRecipe> recipes = new ArrayList<>(event.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.WATER_WASHING.get()));
                recipes.removeIf(r -> !r.matches(new SimpleContainer(event.getEntity().getOffhandItem()), event.getLevel()));
                finalRecipes = recipes;
            }
        }
        for (WaterWashingRecipe recipe : finalRecipes) {
            ItemStack stack = recipe.assemble(new SimpleContainer(), event.getLevel().registryAccess());
            addOrDropStack(event.getEntity(), stack);
            if (!selectingWater) {
                if (isWaterBottle(event.getEntity().getOffhandItem())) {
                    event.getEntity().setItemInHand(InteractionHand.OFF_HAND, Items.GLASS_BOTTLE.getDefaultInstance());
                    event.getEntity().getMainHandItem().shrink(1);
                } else {
                    event.getEntity().setItemInHand(InteractionHand.MAIN_HAND, Items.GLASS_BOTTLE.getDefaultInstance());
                    event.getEntity().getOffhandItem().shrink(1);
                }
            } else {
                event.getItemStack().shrink(1);
            }
        }
        if (!finalRecipes.isEmpty()) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            return true;
        }

        return false;
    }

    protected static boolean isWaterBottle(ItemStack stack) {
        return stack.is(Items.POTION) && PotionUtils.getPotion(stack) == Potions.WATER;
    }

    protected static BlockHitResult getPlayerPOVHitResult(Level pLevel, Player pPlayer, ClipContext.Fluid pFluidMode) {
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        Vec3 vec3 = pPlayer.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = pPlayer.getBlockReach();
        Vec3 vec31 = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
    }


    protected static void addOrDropStack(Player player, ItemStack stack) {
        if (!stack.isEmpty()) {
            if (!player.addItem(stack)) {
                ItemEntity item = player.drop(stack, false);
                if (item != null)
                    player.level().addFreshEntity(item);
            }
        }
    }
}
