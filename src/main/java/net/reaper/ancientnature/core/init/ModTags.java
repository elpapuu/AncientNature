package net.reaper.ancientnature.core.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.reaper.ancientnature.AncientNature;

public class ModTags {

    public static class Items{

        public static final TagKey<Item> FOSSILS = bind("fossils");
        public static final TagKey<Item> ANIMAL_AMBERS = bind("animal_ambers");
        private static TagKey<Item> bind(String pName) {
            return TagKey.create(Registries.ITEM, AncientNature.modLoc(pName));
        }
    }

    public static class Entities{

        public static final TagKey<EntityType<?>> FISHES = bind("fishes");
        public static final TagKey<EntityType<?>> HERBIVORES = bind("herbivores");

        private static TagKey<EntityType<?>> bind(String pName) {
            return TagKey.create(Registries.ENTITY_TYPE, AncientNature.modLoc(pName));
        }
    }
}
