package net.reaper.ancientnature.common.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.common.item.ScarySkull;
import net.reaper.ancientnature.core.init.ModEffects;

import java.util.List;

@Mod.EventBusSubscriber
public class ModCommonEvents {

    @SubscribeEvent
    public static void onPlayerEquip(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        boolean isWearingSkull = player.getArmorSlots().iterator().next().getItem() instanceof ScarySkull;

        List<LivingEntity> nearbyEntities = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(12));

        for (LivingEntity entity : nearbyEntities) {
            if (!(entity instanceof Player)) {
                if (isWearingSkull) {
                    if (!entity.hasEffect(ModEffects.FEAR.get())) {
                        entity.addEffect(new MobEffectInstance(ModEffects.FEAR.get(), 40, 0, true, true));
                    }
                } else {
                    if (entity.hasEffect(ModEffects.FEAR.get())) {
                        entity.removeEffect(ModEffects.FEAR.get());
                    }
                }
            }
        }
    }
}
