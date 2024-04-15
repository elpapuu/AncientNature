package net.reaper.ancientnature.core.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.config.AncientNatureConfig;
import net.reaper.ancientnature.common.menu.RevivalStandMenu;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, AncientNature.MOD_ID);

    public static final RegistryObject<MenuType<RevivalStandMenu>> REVIVAL_STAND = MENUS.register("revival_stand", () -> IForgeMenuType.create(RevivalStandMenu::new));
}
