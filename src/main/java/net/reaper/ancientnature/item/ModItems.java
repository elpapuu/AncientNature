package net.reaper.ancientnature.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.reaper.ancientnature.AncientNature;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.procedures.CambrianFossilCleanProcedure;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AncientNature.MOD_ID);
    public static final RegistryObject<Item> AMBER = ITEMS.register("amber",
            () ->new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOSQUITO_AMBER = ITEMS.register("mosquito_amber",
            () ->new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAMBRIAN_FOSSIL = ITEMS.register("cambrian_fossil",
            () ->new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANOMALOCARIS_FOSSIL = ITEMS.register("anomalocaris_fossil",
            () ->new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}

