package me.ez.orebushes;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class WorldGen {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registries.CONFIGURED_FEATURE, Main.MOD_ID);

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, Main.MOD_ID);

    //Overworld
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COAL_BUSH_BLOCK = singleBush("coal_bush_block", Init.COAL_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COAL_BUSH_PATCH = overworldPatch("coal_bush_patch", COAL_BUSH_BLOCK, 12);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> COAL_BUSH_PLACED = placed("coal_bush_placed", COAL_BUSH_PATCH, 25);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> IRON_BUSH_BLOCK = singleBush("iron_bush_block", Init.IRON_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> IRON_BUSH_PATCH = overworldPatch("iron_bush_patch", IRON_BUSH_BLOCK, 10);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> IRON_BUSH_PLACED = placed("iron_bush_placed", IRON_BUSH_PATCH, 35);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GOLD_BUSH_BLOCK = singleBush("gold_bush_block", Init.GOLD_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GOLD_BUSH_PATCH = overworldPatch("gold_bush_patch", GOLD_BUSH_BLOCK, 6);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> GOLD_BUSH_PLACED = placed("gold_bush_placed", GOLD_BUSH_PATCH, 70);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> EMERALD_BUSH_BLOCK = singleBush("emerald_bush_block", Init.EMERALD_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> EMERALD_BUSH_PATCH = overworldPatch("emerald_bush_patch", EMERALD_BUSH_BLOCK, 4);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> EMERALD_BUSH_PLACED = placed("emerald_bush_placed", EMERALD_BUSH_PATCH, 90);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> REDSTONE_BUSH_BLOCK = singleBush("redstone_bush_block", Init.REDSTONE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> REDSTONE_BUSH_PATCH = overworldPatch("redstone_bush_patch", REDSTONE_BUSH_BLOCK, 8);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> REDSTONE_BUSH_PLACED = placed("redstone_bush_placed", REDSTONE_BUSH_PATCH, 45);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> LAPIS_BUSH_BLOCK = singleBush("lapis_bush_block", Init.LAPIS_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> LAPIS_BUSH_PATCH = overworldPatch("lapis_bush_patch", LAPIS_BUSH_BLOCK, 8);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> LAPIS_BUSH_PLACED = placed("lapis_bush_placed", LAPIS_BUSH_PATCH, 50);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> DIAMOND_BUSH_BLOCK = singleBush("diamond_bush_block", Init.DIAMOND_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> DIAMOND_BUSH_PATCH = overworldPatch("diamond_bush_patch", DIAMOND_BUSH_BLOCK, 3);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> DIAMOND_BUSH_PLACED = placed("diamond_bush_placed", DIAMOND_BUSH_PATCH, 140);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COPPER_BUSH_BLOCK = singleBush("copper_bush_block", Init.COPPER_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COPPER_BUSH_PATCH = overworldPatch("copper_bush_patch", COPPER_BUSH_BLOCK, 10);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> COPPER_BUSH_PLACED = placed("copper_bush_placed", COPPER_BUSH_PATCH, 35);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> SUGAR_BUSH_BLOCK = singleBush("sugar_bush_block", Init.SUGAR_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> SUGAR_BUSH_PATCH = overworldPatch("sugar_bush_patch", SUGAR_BUSH_BLOCK, 12);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> SUGAR_BUSH_PLACED = placed("sugar_bush_placed", SUGAR_BUSH_PATCH, 20);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GOLDEN_APPLE_BUSH_BLOCK = singleBush("golden_apple_bush_block", Init.GOLDEN_APPLE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GOLDEN_APPLE_BUSH_PATCH = overworldPatch("golden_apple_bush_patch", GOLDEN_APPLE_BUSH_BLOCK, 6);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> GOLDEN_APPLE_BUSH_PLACED = placed("golden_apple_bush_placed", GOLDEN_APPLE_BUSH_PATCH, 50);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> AMETHYST_BUSH_BLOCK = singleBush("amethyst_bush_block", Init.AMETHYST_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> AMETHYST_BUSH_PATCH = overworldPatch("amethyst_bush_patch", AMETHYST_BUSH_BLOCK, 4);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> AMETHYST_BUSH_PLACED = placed("amethyst_bush_placed", AMETHYST_BUSH_PATCH, 80);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> EXPERIENCE_BUSH_BLOCK = singleBush("experience_bush_block", Init.EXPERIENCE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> EXPERIENCE_BUSH_PATCH = overworldPatch("experience_bush_patch", EXPERIENCE_BUSH_BLOCK, 3);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> EXPERIENCE_BUSH_PLACED = placed("experience_bush_placed", EXPERIENCE_BUSH_PATCH, 200);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ECHO_SHARD_BUSH_BLOCK = singleBush("echo_shard_bush_block", Init.ECHO_SHARD_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ECHO_SHARD_BUSH_PATCH = overworldPatch("echo_shard_bush_patch", ECHO_SHARD_BUSH_BLOCK, 3);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> ECHO_SHARD_BUSH_PLACED = placed("echo_shard_bush_placed", ECHO_SHARD_BUSH_PATCH, 250);

    //Nether
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> QUARTZ_BUSH_BLOCK = singleBush("quartz_bush_block", Init.QUARTZ_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> QUARTZ_BUSH_PATCH = netherPatch("quartz_bush_patch", QUARTZ_BUSH_BLOCK, 10);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> QUARTZ_BUSH_PLACED = placed("quartz_bush_placed", QUARTZ_BUSH_PATCH, 30);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GLOWSTONE_BUSH_BLOCK = singleBush("glowstone_bush_block", Init.GLOWSTONE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GLOWSTONE_BUSH_PATCH = netherPatch("glowstone_bush_patch", GLOWSTONE_BUSH_BLOCK, 6);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> GLOWSTONE_BUSH_PLACED = placed("glowstone_bush_placed", GLOWSTONE_BUSH_PATCH, 70);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> NETHERITE_BUSH_BLOCK = singleBush("netherite_bush_block", Init.NETHERITE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> NETHERITE_BUSH_PATCH = netherPatch("netherite_bush_patch", NETHERITE_BUSH_BLOCK, 2);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> NETHERITE_BUSH_PLACED = placed("netherite_bush_placed", NETHERITE_BUSH_PATCH, 400);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> BLAZE_BUSH_BLOCK = singleBush("blaze_bush_block", Init.BLAZE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> BLAZE_BUSH_PATCH = netherPatch("blaze_bush_patch", BLAZE_BUSH_BLOCK, 4);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> BLAZE_BUSH_PLACED = placed("blaze_bush_placed", BLAZE_BUSH_PATCH, 80);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ANCIENT_DEBRIS_BUSH_BLOCK = singleBush("ancient_debris_bush_block", Init.ANCIENT_DEBRIS_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ANCIENT_DEBRIS_BUSH_PATCH = netherPatch("ancient_debris_bush_patch", ANCIENT_DEBRIS_BUSH_BLOCK, 2);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> ANCIENT_DEBRIS_BUSH_PLACED = placed("ancient_debris_bush_placed", ANCIENT_DEBRIS_BUSH_PATCH, 300);

    //End
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ENDER_PEARL_BUSH_BLOCK = singleBush("ender_pearl_bush_block", Init.ENDER_PEARL_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ENDER_PEARL_BUSH_PATCH = endPatch("ender_pearl_bush_patch", ENDER_PEARL_BUSH_BLOCK, 4);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> ENDER_PEARL_BUSH_PLACED = placed("ender_pearl_bush_placed", ENDER_PEARL_BUSH_PATCH, 130);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ENDER_EYE_BUSH_BLOCK = singleBush("ender_eye_bush_block", Init.ENDER_EYE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> ENDER_EYE_BUSH_PATCH = endPatch("ender_eye_bush_patch", ENDER_EYE_BUSH_BLOCK, 3);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> ENDER_EYE_BUSH_PLACED = placed("ender_eye_bush_placed", ENDER_EYE_BUSH_PATCH, 200);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> CHORUS_BUSH_BLOCK = singleBush("chorus_bush_block", Init.CHORUS_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> CHORUS_BUSH_PATCH = endPatch("chorus_bush_patch", CHORUS_BUSH_BLOCK, 6);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> CHORUS_BUSH_PLACED = placed("chorus_bush_placed", CHORUS_BUSH_PATCH, 80);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> SHULKER_SHELL_BUSH_BLOCK = singleBush("shulker_shell_bush_block", Init.SHULKER_SHELL_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> SHULKER_SHELL_BUSH_PATCH = endPatch("shulker_shell_bush_patch", SHULKER_SHELL_BUSH_BLOCK, 2);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> SHULKER_SHELL_BUSH_PLACED = placed("shulker_shell_bush_placed", SHULKER_SHELL_BUSH_PATCH, 350);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> DRAGON_BREATH_BUSH_BLOCK = singleBush("dragon_breath_bush_block", Init.DRAGON_BREATH_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> DRAGON_BREATH_BUSH_PATCH = endPatch("dragon_breath_bush_patch", DRAGON_BREATH_BUSH_BLOCK, 2);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> DRAGON_BREATH_BUSH_PLACED = placed("dragon_breath_bush_placed", DRAGON_BREATH_BUSH_PATCH, 250);

    private static DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> singleBush(String name, DeferredHolder<Block, ? extends Block> bush) {
        return CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(bush.get().defaultBlockState()
                        .setValue(me.ez.orebushes.Common.Bushes.AbstractModBushBlock.AGE, 3)))));
    }

    private static DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> overworldPatch(String name, DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> singleBush, int tries) {
        return CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(tries,
                        PlacementUtils.inlinePlaced(singleBush,
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.GRASS_BLOCK, Blocks.FARMLAND)),
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR))))));
    }

    private static DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> netherPatch(String name, DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> singleBush, int tries) {
        return CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(tries,
                        PlacementUtils.inlinePlaced(singleBush,
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.NETHERRACK)),
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR))))));
    }

    private static DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> endPatch(String name, DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> singleBush, int tries) {
        return CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(tries,
                        PlacementUtils.inlinePlaced(singleBush,
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.END_STONE)),
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR))))));
    }

    private static DeferredHolder<PlacedFeature, PlacedFeature> placed(String name, DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> patch, int rarity) {
        return PLACED_FEATURES.register(name, () -> new PlacedFeature(patch, List.of(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome())));
    }

}
