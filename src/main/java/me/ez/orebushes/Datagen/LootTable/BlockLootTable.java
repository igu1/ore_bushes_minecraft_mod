package me.ez.orebushes.Datagen.LootTable;

import me.ez.orebushes.Init;
import me.ez.orebushes.Common.Bushes.AbstractModBushBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockLootTable extends BlockLootSubProvider {

    public BlockLootTable(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Init.BUSHES.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
    }

    @Override
    protected void generate() {
        // Item + the count a ripe plant drops per harvest. Must match the bush's
        // getDropItem(), because a ripe bush dropped on break yields the same.
        bush(Init.COAL_BUSH.get(), Items.COAL, 1);
        bush(Init.IRON_BUSH.get(), Items.RAW_IRON, 1);
        bush(Init.GOLD_BUSH.get(), Items.RAW_GOLD, 1);
        bush(Init.EMERALD_BUSH.get(), Items.EMERALD, 1);
        bush(Init.DIAMOND_BUSH.get(), Items.DIAMOND, 1);
        bush(Init.REDSTONE_BUSH.get(), Items.REDSTONE, 2);
        bush(Init.LAPIS_BUSH.get(), Items.LAPIS_LAZULI, 1);
        bush(Init.COPPER_BUSH.get(), Items.RAW_COPPER, 2);

        bush(Init.QUARTZ_BUSH.get(), Items.QUARTZ, 2);
        bush(Init.GLOWSTONE_BUSH.get(), Items.GLOWSTONE_DUST, 2);
        bush(Init.NETHERITE_BUSH.get(), Items.NETHERITE_INGOT, 1);

        bush(Init.AMETHYST_BUSH.get(), Items.AMETHYST_SHARD, 2);
        bush(Init.EXPERIENCE_BUSH.get(), Items.EXPERIENCE_BOTTLE, 3);
        bush(Init.ECHO_SHARD_BUSH.get(), Items.ECHO_SHARD, 2);
        bush(Init.GOLDEN_APPLE_BUSH.get(), Items.GOLDEN_APPLE, 2);
        bush(Init.SUGAR_BUSH.get(), Items.SUGAR, 2);

        bush(Init.ANCIENT_DEBRIS_BUSH.get(), Items.ANCIENT_DEBRIS, 1);
        bush(Init.BLAZE_BUSH.get(), Items.BLAZE_ROD, 1);

        bush(Init.ENDER_PEARL_BUSH.get(), Items.ENDER_PEARL, 2);
        bush(Init.ENDER_EYE_BUSH.get(), Items.ENDER_EYE, 1);
        bush(Init.CHORUS_BUSH.get(), Items.CHORUS_FRUIT, 2);
        bush(Init.SHULKER_SHELL_BUSH.get(), Items.SHULKER_SHELL, 1);
        bush(Init.DRAGON_BREATH_BUSH.get(), Items.DRAGON_BREATH, 1);
    }

    private void bush(BushBlock bushblock, ItemLike itemLike, int perHarvest) {
        AbstractModBushBlock plant = (AbstractModBushBlock) bushblock;
        LootTable.Builder table = LootTable.lootTable();

        // A not-yet-spent plant returns its starter seed when broken, so it can be
        // relocated. Once spent (harvests == its lifetime limit) it drops NO seed,
        // so a plant is a finite resource and cannot be farmed forever.
        for (int harvests = 0; harvests < plant.profile().defaultHarvestLimit(); harvests++) {
            table.withPool(LootPool.lootPool()
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(bushblock)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(AbstractModBushBlock.HARVESTS, harvests)))
                    .add(LootItem.lootTableItem(bushblock)));
        }

        // A ripe, non-spent plant also drops its resource. Pools stop at the default
        // lifetime so a spent state never yields a resource.
        for (int harvests = 0; harvests < plant.profile().defaultHarvestLimit(); harvests++) {
            LootPool.Builder pool = LootPool.lootPool()
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(bushblock)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(AbstractModBushBlock.AGE, 3)
                                    .hasProperty(AbstractModBushBlock.HARVESTS, harvests)))
                    .add(LootItem.lootTableItem(itemLike));
            if (perHarvest > 1) {
                pool = pool.apply(SetItemCountFunction.setCount(
                        net.minecraft.world.level.storage.loot.providers.number.ConstantValue.exactly(perHarvest)));
            }
            table.withPool(pool);
        }
        add(bushblock, applyExplosionDecay(bushblock, table));
    }
}
