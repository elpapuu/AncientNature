package net.reaper.ancientnature;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.reaper.ancientnature.core.init.*;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AncientNature.MOD_ID)
public class AncientNature {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ancientnature";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public AncientNature() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus);
        ModBlockEntities.TES.register(modEventBus);
    }

}
