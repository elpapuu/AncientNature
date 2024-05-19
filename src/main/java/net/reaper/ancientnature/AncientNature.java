package net.reaper.ancientnature;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.reaper.ancientnature.common.config.AncientNatureConfig;
import net.reaper.ancientnature.core.datagen.client.ModBlockStatesProvider;
import net.reaper.ancientnature.core.init.*;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AncientNature.MOD_ID)
public class AncientNature {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ancientnature";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public AncientNature() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AncientNatureConfig.SPEC);

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus);



        ModBlockEntities.TES.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
    }

    public static ResourceLocation modLoc(String name){
        return new ResourceLocation(MOD_ID, name);
    }

    public static ResourceLocation entityTexture(String name){
        return modLoc("textures/entity/" + name);
    }

}
