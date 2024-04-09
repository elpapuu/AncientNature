package net.reaper.ancientnature.core.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.reaper.ancientnature.AncientNature;

public class ModTags {

    public static class Items{

        public static final TagKey<Item> FOSSILS = bind("fossils");
        public static final TagKey<Item> AMBER = bind("amber");

        private static TagKey<Item> bind(String pName) {
            return TagKey.create(Registries.ITEM, AncientNature.modLoc(pName));
        }
    }
}
