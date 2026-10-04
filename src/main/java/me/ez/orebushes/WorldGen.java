package me.ez.orebushes;

import me.ez.orebushes.Common.Bushes.ResourcePlantProfile;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

/**
 * Natural bush generation on 1.21.1.
 *
 * <p>Configured and placed features are registered at runtime and attached to
 * biomes through the {@code forge:add_features} biome modifiers in
 * {@code data/orebushes/forge/biome_modifier/}.
 *
 * <p><b>Only common resources (tiers 1-2) spawn naturally.</b> Tiers 3 and 4 stay
 * craft-only, so a rare plant can never shortcut you to a resource. Wild plants
 * are extremely rare and only appear where that plant's own substrate can support
 * it, so a planted starter and a wild discovery obey the same placement rule.
 */
public class WorldGen {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registries.CONFIGURED_FEATURE, Main.MOD_ID);

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, Main.MOD_ID);

    //Overworld (common only: tiers 1-2)

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COAL_BUSH_BLOCK = singleBush("coal_bush_block", Init.COAL_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COAL_BUSH_PATCH = patch("coal_bush_patch", COAL_BUSH_BLOCK, ResourcePlantProfile.COAL, 12);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> COAL_BUSH_PLACED = placed("coal_bush_placed", COAL_BUSH_PATCH, 260);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COPPER_BUSH_BLOCK = singleBush("copper_bush_block", Init.COPPER_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> COPPER_BUSH_PATCH = patch("copper_bush_patch", COPPER_BUSH_BLOCK, ResourcePlantProfile.COPPER, 10);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> COPPER_BUSH_PLACED = placed("copper_bush_placed", COPPER_BUSH_PATCH, 300);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> SUGAR_BUSH_BLOCK = singleBush("sugar_bush_block", Init.SUGAR_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> SUGAR_BUSH_PATCH = patch("sugar_bush_patch", SUGAR_BUSH_BLOCK, ResourcePlantProfile.SUGAR, 12);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> SUGAR_BUSH_PLACED = placed("sugar_bush_placed", SUGAR_BUSH_PATCH, 340);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> IRON_BUSH_BLOCK = singleBush("iron_bush_block", Init.IRON_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> IRON_BUSH_PATCH = patch("iron_bush_patch", IRON_BUSH_BLOCK, ResourcePlantProfile.IRON, 10);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> IRON_BUSH_PLACED = placed("iron_bush_placed", IRON_BUSH_PATCH, 420);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> REDSTONE_BUSH_BLOCK = singleBush("redstone_bush_block", Init.REDSTONE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> REDSTONE_BUSH_PATCH = patch("redstone_bush_patch", REDSTONE_BUSH_BLOCK, ResourcePlantProfile.REDSTONE, 8);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> REDSTONE_BUSH_PLACED = placed("redstone_bush_placed", REDSTONE_BUSH_PATCH, 460);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> LAPIS_BUSH_BLOCK = singleBush("lapis_bush_block", Init.LAPIS_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> LAPIS_BUSH_PATCH = patch("lapis_bush_patch", LAPIS_BUSH_BLOCK, ResourcePlantProfile.LAPIS, 8);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> LAPIS_BUSH_PLACED = placed("lapis_bush_placed", LAPIS_BUSH_PATCH, 480);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GOLD_BUSH_BLOCK = singleBush("gold_bush_block", Init.GOLD_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GOLD_BUSH_PATCH = patch("gold_bush_patch", GOLD_BUSH_BLOCK, ResourcePlantProfile.GOLD, 6);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> GOLD_BUSH_PLACED = placed("gold_bush_placed", GOLD_BUSH_PATCH, 560);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> AMETHYST_BUSH_BLOCK = singleBush("amethyst_bush_block", Init.AMETHYST_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> AMETHYST_BUSH_PATCH = patch("amethyst_bush_patch", AMETHYST_BUSH_BLOCK, ResourcePlantProfile.AMETHYST, 4);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> AMETHYST_BUSH_PLACED = placed("amethyst_bush_placed", AMETHYST_BUSH_PATCH, 640);

    //Nether (common only: tiers 1-2)

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> QUARTZ_BUSH_BLOCK = singleBush("quartz_bush_block", Init.QUARTZ_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> QUARTZ_BUSH_PATCH = patch("quartz_bush_patch", QUARTZ_BUSH_BLOCK, ResourcePlantProfile.QUARTZ, 10);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> QUARTZ_BUSH_PLACED = placed("quartz_bush_placed", QUARTZ_BUSH_PATCH, 380);

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GLOWSTONE_BUSH_BLOCK = singleBush("glowstone_bush_block", Init.GLOWSTONE_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> GLOWSTONE_BUSH_PATCH = patch("glowstone_bush_patch", GLOWSTONE_BUSH_BLOCK, ResourcePlantProfile.GLOWSTONE, 6);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> GLOWSTONE_BUSH_PLACED = placed("glowstone_bush_placed", GLOWSTONE_BUSH_PATCH, 520);

    //End (common only: tiers 1-2)

    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> CHORUS_BUSH_BLOCK = singleBush("chorus_bush_block", Init.CHORUS_BUSH);
    private static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> CHORUS_BUSH_PATCH = patch("chorus_bush_patch", CHORUS_BUSH_BLOCK, ResourcePlantProfile.CHORUS, 6);
    public static final DeferredHolder<PlacedFeature, PlacedFeature> CHORUS_BUSH_PLACED = placed("chorus_bush_placed", CHORUS_BUSH_PATCH, 300);

    private static DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> singleBush(String name, DeferredHolder<Block, ? extends Block> bush) {
        return CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(bush.get().defaultBlockState()
                        .setValue(me.ez.orebushes.Common.Bushes.AbstractModBushBlock.AGE, 3)))));
    }

    /**
     * A rare patch of a single bush. The plant only spawns on its own substrate,
     * mirroring the planting rule, so wild growth never appears out of place.
     */
    private static DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> patch(
            String name,
            DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> singleBush,
            ResourcePlantProfile profile,
            int tries) {
        return CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(tries,
                        PlacementUtils.inlinePlaced(singleBush,
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), profile.substrate)),
                                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(net.minecraft.world.level.block.Blocks.AIR))))));
    }

    private static DeferredHolder<PlacedFeature, PlacedFeature> placed(String name, DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> patch, int rarity) {
        return PLACED_FEATURES.register(name, () -> new PlacedFeature(patch, List.of(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome())));
    }

}
