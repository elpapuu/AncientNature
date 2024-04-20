package net.reaper.ancientnature.core.datagen.server;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.loot_modifiers.ChanceItemModifier;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModTags;

public class ModLootModifierProvider extends GlobalLootModifierProvider {
    public ModLootModifierProvider(PackOutput output) {
        super(output, AncientNature.MOD_ID);
    }

    @Override
    protected void start() {
        add("fish_roe_pufferfish", new ChanceItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModTags.Entities.FISHES)).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.023f, 0.02f).build()
        }, ModItems.FISH_ROE.get()));
    }
}
