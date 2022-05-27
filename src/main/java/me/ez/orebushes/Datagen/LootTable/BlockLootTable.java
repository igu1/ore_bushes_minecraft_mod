package me.ez.orebushes.Datagen.LootTable;

import me.ez.orebushes.Init;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class BlockLootTable extends BlockLoot {

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Init.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    @Override
    protected void addTables() {
        addBushLootTable(Init.COAL_BUSH.get(), Items.COAL);
        addBushLootTable(Init.IRON_BUSH.get(), Items.IRON_NUGGET);
        addBushLootTable(Init.GOLD_BUSH.get(), Items.GOLD_NUGGET);
        addBushLootTable(Init.EMERALD_BUSH.get(), Init.EMERALD_NUGGET.get());
        addBushLootTable(Init.DIAMOND_BUSH.get(), Init.DIAMOND_NUGGET.get());
        addBushLootTable(Init.REDSTONE_BUSH.get(), Items.REDSTONE);
        addBushLootTable(Init.LAPIS_BUSH.get(), Items.LAPIS_LAZULI);
        addBushLootTable(Init.COPPER_BUSH.get(), Init.COPPER_NUGGET.get());

        addBushLootTable(Init.QUARTZ_BUSH.get(), Items.QUARTZ);
        addBushLootTable(Init.GLOWSTONE_BUSH.get(), Items.GLOWSTONE_DUST);
        addBushLootTable(Init.NETHERITE_BUSH.get(), Init.NETHERITE_NUGGET.get());

    }

    private void addBushLootTable(BushBlock bushblock, ItemLike itemLike){
        this.add(bushblock, (block) ->
                applyExplosionDecay(block,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(bushblock)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(BlockStateProperties.AGE_3, 3)))
                                                .add(LootItem.lootTableItem(itemLike))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))

                                .withPool(
                                        LootPool.lootPool()
                                                .when(LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(bushblock)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(SweetBerryBushBlock.AGE, 2)))
                                                .add(LootItem.lootTableItem(itemLike))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
    }
}
