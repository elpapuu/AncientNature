package net.reaper.ancientnature;

import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.client.event.DinoHealthBarRenderer;
import net.reaper.ancientnature.common.config.AncientNatureConfig;
import net.reaper.ancientnature.common.messages.NetworkHandler;
import net.reaper.ancientnature.common.messages.util.EventHandlerRegistry;
import net.reaper.ancientnature.common.messages.util.LevelEvents;
import net.reaper.ancientnature.common.util.EntityPlacementUtil;
import net.reaper.ancientnature.core.init.*;
import net.reaper.ancientnature.core.proxy.ClientProxy;
import net.reaper.ancientnature.core.proxy.CommonProxy;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AncientNature.MOD_ID)
public class AncientNature {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ancientnature";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);


    public AncientNature() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AncientNatureConfig.SPEC);
        ModCreativeModTabs.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.TES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new DinoHealthBarRenderer(Component.translatable("gui.Dino")));
        modEventBus.addListener(this::addCreative);
        EventHandlerRegistry.LevelEventHandler.registerCommonHandler(new ResourceLocation(MOD_ID, "common_process"), new LevelEvents());
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        Reflection.initialize(LevelEvents.class);
        event.enqueueWork(() -> {
            EntityPlacementUtil.entityPlacement();
        });
        NetworkHandler.register();
    }

    public static ResourceLocation modLoc(String name){
        return new ResourceLocation(MOD_ID, name);
    }

    public static ResourceLocation entityTexture(String name){
        return modLoc("textures/entity/" + name);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Adds everything to Search Tab, since Doot's too lazy to /give during debug.
        if (event.getTabKey() == CreativeModeTabs.SEARCH) {
            List<Item> items = ModItems.ITEMS.getEntries()
                    .stream()
                    .map(RegistryObject::get)
                    .toList();
            for (Item item : items) {
                event.accept(item);
            }
        }
    }
}
