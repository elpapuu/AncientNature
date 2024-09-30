package net.reaper.ancientnature.common.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.item.TyrantAxe;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class LivingEquipmentChange
{
    @SubscribeEvent()
    public static void onPlayerEquipmentChange(LivingEquipmentChangeEvent event)
    {
        if(event.getEntity() instanceof Player player)
        {
            if(player.getMainHandItem().getItem() instanceof TyrantAxe)
            {
                if(event.getSlot() == EquipmentSlot.OFFHAND)
                {
                    player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);

                }
            }
        }
    }
}
