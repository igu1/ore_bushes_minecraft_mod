package me.ez.orebushes;

import me.ez.orebushes.Common.Bushes.AbstractModBushBlock;
import me.ez.orebushes.Common.Bushes.ResourcePlantProfile;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.stream.Stream;

/**
 * Natural bush generation on 26.1.
 *
 * <p>Configured/placed features are datapack registries since 1.21, so they are
 * bootstrapped with a {@link BootstrapContext} and written to
 * {@code data/orebushes/worldgen/...} during datagen. Plain
 * {@code neoforge:add_features} biome modifiers under
 * {@code data/orebushes/neoforge/biome_modifier/} attach the placed features to
 * the matching dimensions.
 *
 * <p><b>Only common resources (tiers 1-2) spawn naturally.</b> Tiers 3 and 4 stay
 * craft-only, so a rare plant can never shortcut you to a resource. Wild plants
 * are extremely rare and only appear where that plant's own substrate can support
 * it, so a planted starter and a wild discovery obey the same placement rule.
 */
public final class WorldGen {
    private WorldGen() {}

    /** A naturally spawning plant: its block holder, profile and rarity. */
    public record WildBush(DeferredBlock<? extends Block> bush, ResourcePlantProfile profile, int rarity) {}

    /** Rarity is "one attempt per N chunks"; higher is rarer. */
    private static final List<WildBush> OVERWORLD = List.of(
            new WildBush(Init.COAL_BUSH, ResourcePlantProfile.COAL, 260),
            new WildBush(Init.COPPER_BUSH, ResourcePlantProfile.COPPER, 300),
            new WildBush(Init.SUGAR_BUSH, ResourcePlantProfile.SUGAR, 340),
            new WildBush(Init.IRON_BUSH, ResourcePlantProfile.IRON, 420),
            new WildBush(Init.REDSTONE_BUSH, ResourcePlantProfile.REDSTONE, 460),
            new WildBush(Init.LAPIS_BUSH, ResourcePlantProfile.LAPIS, 480),
            new WildBush(Init.GOLD_BUSH, ResourcePlantProfile.GOLD, 560),
            new WildBush(Init.AMETHYST_BUSH, ResourcePlantProfile.AMETHYST, 640));

    private static final List<WildBush> NETHER = List.of(
            new WildBush(Init.QUARTZ_BUSH, ResourcePlantProfile.QUARTZ, 380),
            new WildBush(Init.GLOWSTONE_BUSH, ResourcePlantProfile.GLOWSTONE, 520));

    private static final List<WildBush> END = List.of(
            new WildBush(Init.CHORUS_BUSH, ResourcePlantProfile.CHORUS, 300));

    public static List<WildBush> overworld() { return OVERWORLD; }
    public static List<WildBush> nether() { return NETHER; }
    public static List<WildBush> end() { return END; }

    public static List<WildBush> all() {
        return Stream.of(OVERWORLD, NETHER, END).flatMap(List::stream).toList();
    }

    /** {@code orebushes:<bush path>_wild} — the configured feature id. */
    public static String configuredName(WildBush bush) {
        return bush.bush().getId().getPath() + "_wild";
    }

    /** {@code orebushes:<bush path>_wild_placed} — the placed feature id. */
    public static String placedName(WildBush bush) {
        return bush.bush().getId().getPath() + "_wild_placed";
    }

    // ------------------------------------------------------------------
    // Datagen bootstrap
    // ------------------------------------------------------------------

    public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        for (WildBush bush : all()) {
            context.register(configuredKey(bush), new ConfiguredFeature<>(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(BlockStateProvider.simple(
                            bush.bush().get().defaultBlockState()
                                    .setValue(AbstractModBushBlock.AGE, AbstractModBushBlock.MAX_AGE)
                                    .setValue(AbstractModBushBlock.HARVESTS, 0)))));
        }
    }

    public static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);
        for (WildBush bush : all()) {
            PlacementUtils.register(context, placedKey(bush),
                    configured.getOrThrow(configuredKey(bush)),
                    List.of(
                            RarityFilter.onAverageOnceEvery(bush.rarity()),
                            InSquarePlacement.spread(),
                            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                            // The same substrate the plant itself requires.
                            BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(
                                    Direction.DOWN.getUnitVec3i(), bush.profile().substrate)),
                            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE),
                            BiomeFilter.biome()));
        }
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(WildBush bush) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id(configuredName(bush)));
    }

    private static ResourceKey<PlacedFeature> placedKey(WildBush bush) {
        return ResourceKey.create(Registries.PLACED_FEATURE, id(placedName(bush)));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Main.MOD_ID, path);
    }
}
