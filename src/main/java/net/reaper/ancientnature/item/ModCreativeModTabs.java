package net.reaper.ancientnature.item;

import net.reaper.ancientnature.AncientNature;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.block.ModBlocks;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AncientNature.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ANCIENTNATURE_TAB = CREATIVE_MODE_TABS.register("ancientnature_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AMBER.get()))
                    .title(Component.translatable("creativetab.ancientnature_tab"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModItems.AMBER.get());
                        pOutput.accept(ModItems.MOSQUITO_AMBER.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_AMBER.get());
                        pOutput.accept(ModItems.CAMBRIAN_FOSSIL.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_CAMBRIAN_FOSSIL.get());
                        pOutput.accept(ModItems.ANOMALOCARIS_FOSSIL.get());

                    })

                    .build());



public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TABS.register(eventBus);
}

}
