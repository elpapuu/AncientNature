package net.reaper.ancientnature.common.entity.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalDiet {
    private final List<Item> items;
    private final List<Block> blocks;

    private AnimalDiet(Builder builder) {
        this.items = builder.items;
        this.blocks = builder.blocks;
    }

    public boolean test(ItemStack item) {
        return items.contains(item.getItem());
    }

    public static class Builder {
        private final List<Item> items = new ArrayList<>();
        private final List<Block> blocks = new ArrayList<>();

        public Builder addItems(Item... items) {
            Collections.addAll(this.items, items);
            return this;
        }

        public Builder addBlocks(Block... blocks) {
            Collections.addAll(this.blocks, blocks);
            return this;
        }

        public AnimalDiet build() {
            return new AnimalDiet(this);
        }
}

    public List<Item> getItems() {
        return items;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
