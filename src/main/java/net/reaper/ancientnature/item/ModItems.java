package net.reaper.ancientnature.item;

import net.reaper.ancientnature.AncientNature;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


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

