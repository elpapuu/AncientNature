package net.reaper.ancientnature.core.init;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.*;
import net.reaper.ancientnature.common.entity.water.Anomalocaris;
import net.reaper.ancientnature.common.entity.water.Arandaspis;
import net.reaper.ancientnature.common.entity.water.DunkleosteusEntity;
import net.reaper.ancientnature.common.entity.water.Paranogmius;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AncientNature.MOD_ID);

    public static final RegistryObject<EntityType<Arandaspis>> ARANDASPIS =
            ENTITY_TYPES.register("arandaspis", () -> EntityType.Builder.of(Arandaspis::new, MobCategory.WATER_CREATURE)
                    .sized(0.5f, 0.5f).build("arandaspis"));

    public static final RegistryObject<EntityType<TuataraEntity>> TUATARA =
            ENTITY_TYPES.register("tuatara", () -> EntityType.Builder.of(TuataraEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.7f).build("tuatara"));

    public static final RegistryObject<EntityType<Anomalocaris>> ANOMALOCARIS =
            register("anomalocaris", () -> EntityType.Builder.of(Anomalocaris::new, MobCategory.WATER_CREATURE)
                    .sized(1f, 0.2f));
    public static final RegistryObject<EntityType<Paranogmius>> PARANOGMIUS =
            register("paranogmius", () -> EntityType.Builder.of(Paranogmius::new, MobCategory.WATER_CREATURE)
                    .sized(1.6f, 1.5f));
    public static final RegistryObject<EntityType<DunkleosteusEntity>> DUNKLEOSTEUS =
            ENTITY_TYPES.register("dunkleosteus", () -> EntityType.Builder.of(DunkleosteusEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("dunkleosteus"));
    public static final RegistryObject<EntityType<DodoEntity>> DODO =
            ENTITY_TYPES.register("dodo", () -> EntityType.Builder.of(DodoEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("dodo"));
    public static final RegistryObject<EntityType<CitipatiEntity>> CITIPATI =
            ENTITY_TYPES.register("citipati", () -> EntityType.Builder.of(CitipatiEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.7f).build("citipati"));
    public static final RegistryObject<EntityType<LythronaxEntity>> LYTHRONAX =
            ENTITY_TYPES.register("lythronax", () -> EntityType.Builder.of(LythronaxEntity::new, MobCategory.CREATURE)
                    .sized(2, 2).build("lythronax"));


    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder){
        return ENTITY_TYPES.register(name, () -> builder.get().build(AncientNature.modLoc(name).toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
