package net.reaper.ancientnature.core.datagen.server;

import com.google.common.collect.Lists;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.reaper.ancientnature.core.datagen.server.loot.ModArcheologyLoot;
import net.reaper.ancientnature.core.datagen.server.loot.ModBlockLoot;

import java.util.Set;

public class ModLoot extends LootTableProvider {
    public ModLoot(PackOutput pOutput) {
        super(pOutput, Set.of(), Lists.newArrayList(
                new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(ModArcheologyLoot::new, LootContextParamSets.ARCHAEOLOGY)
        ));
    }
}
