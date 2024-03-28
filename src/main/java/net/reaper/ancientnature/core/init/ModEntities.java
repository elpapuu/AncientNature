package net.reaper.ancientnature.core.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.water.ArandaspisEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AncientNature.MOD_ID);

    public static final RegistryObject<EntityType<ArandaspisEntity>> ARANDASPIS =
            ENTITY_TYPES.register("arandaspis", () -> EntityType.Builder.of(ArandaspisEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.6f, 0.7f).build("arandaspis"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
