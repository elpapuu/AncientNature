package net.reaper.ancientnature.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.reaper.ancientnature.AncientNature;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AncientNatureConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue BLAZE_POWDER_BURN_TIME, MAX_FUEL, MAX_HEATING;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Revival Stand");
        builder.comment("this is the maxFuel that the revivalStand will need on order to process stuff");
        MAX_FUEL = builder.defineInRange("maxFuel", 1000, 10, Integer.MAX_VALUE);
        builder.comment("this defines what this stand can heat at a maximum per tick");
        MAX_HEATING = builder.defineInRange("maxHeating", 10, 1, 1000000);
        builder.comment("this defines how much fuel the blaze powder will produce in the revival stand");
        BLAZE_POWDER_BURN_TIME = builder.defineInRange("balze poweder burn time", 500, 10, 1000000);
        builder.pop();

        SPEC = builder.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {


    }
}
