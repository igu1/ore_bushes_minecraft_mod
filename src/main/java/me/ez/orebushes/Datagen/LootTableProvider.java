package me.ez.orebushes.Datagen;

import me.ez.orebushes.Datagen.LootTable.BlockLootTable;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {

    public LootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(BlockLootTable::new, LootContextParamSets.BLOCK)), registries);
    }
}
