package net.reaper.ancientnature.core.datagen.server.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaArchaeologyLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModLootTables;

import java.util.function.BiConsumer;

public class ModArcheologyLoot implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
            LootTable.Builder builder = LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemRandomChanceCondition.randomChance(0.9f)).add(LootItem.lootTableItem(ModItems.CAMBRIAN_FOSSIL.get()).setWeight(6)).add(LootItem.lootTableItem(ModItems.ANOMALOCARIS_FOSSIL.get())));
            biConsumer.accept(ModLootTables.SUSPICIOUS_GRAVEL_BRUSH, builder);
    }
}