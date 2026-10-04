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

        addBushLootTable(Init.AMETHYST_BUSH.get(), Items.AMETHYST_SHARD);
        addBushLootTable(Init.EXPERIENCE_BUSH.get(), Items.EXPERIENCE_BOTTLE);
        addBushLootTable(Init.ECHO_SHARD_BUSH.get(), Items.ECHO_SHARD);
        addBushLootTable(Init.GOLDEN_APPLE_BUSH.get(), Items.GOLDEN_APPLE);
        addBushLootTable(Init.SUGAR_BUSH.get(), Items.SUGAR);

        addBushLootTable(Init.ANCIENT_DEBRIS_BUSH.get(), Items.ANCIENT_DEBRIS);
        addBushLootTable(Init.BLAZE_BUSH.get(), Items.BLAZE_ROD);

        addBushLootTable(Init.ENDER_PEARL_BUSH.get(), Items.ENDER_PEARL);
        addBushLootTable(Init.ENDER_EYE_BUSH.get(), Items.ENDER_EYE);
        addBushLootTable(Init.CHORUS_BUSH.get(), Items.CHORUS_FRUIT);
        addBushLootTable(Init.SHULKER_SHELL_BUSH.get(), Items.SHULKER_SHELL);
        addBushLootTable(Init.DRAGON_BREATH_BUSH.get(), Items.DRAGON_BREATH);

    }

    private void addBushLootTable(BushBlock bushblock, ItemLike itemLike){
        AbstractModBushBlock plant = (AbstractModBushBlock) bushblock;
        LootTable.Builder table = LootTable.lootTable();
        // Breaking returns the starter seed at any age or remaining harvest count.
        table.withPool(LootPool.lootPool().add(LootItem.lootTableItem(bushblock)));
        // Ripe, non-spent plants additionally drop their resource. Pools stop at the
        // default lifetime so a spent state (harvests == limit) never yields a resource.
        for (int harvests = 0; harvests < plant.profile().defaultHarvestLimit(); harvests++) {
            table.withPool(LootPool.lootPool()
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(bushblock)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(AbstractModBushBlock.AGE, 3)
                                    .hasProperty(AbstractModBushBlock.HARVESTS, harvests)))
                    .add(LootItem.lootTableItem(itemLike)));
        }
        add(bushblock, applyExplosionDecay(bushblock, table));
    }
}
