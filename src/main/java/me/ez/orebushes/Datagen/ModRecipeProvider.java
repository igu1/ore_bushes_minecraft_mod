package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Common.Bushes.ResourcePlantProfile;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public class ModRecipeProvider extends RecipeProvider {

    private final HolderGetter<Item> items;

    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, Init.BUSH_HARVESTER_BLOCK_ITEM.get())
                .pattern("IHI").pattern("CRC").pattern("IPI")
                .define('I', Items.IRON_INGOT).define('H', Items.HOPPER)
                .define('C', Items.COPPER_INGOT).define('R', Items.REDSTONE)
                .define('P', Items.PISTON).unlockedBy("has_hopper", has(Items.HOPPER)).save(output);

        //Items

        bushsSeedRecipeProvider();
    }

    /**
     * Seed recipe: shape " C " / "SBS" / " C ".
     *
     * <ul>
     *   <li>{@code C} — a non-refundable rarity catalyst that scales with tier
     *       (bone meal → amethyst shard → ender pearl → echo shard). You never get
     *       it back, so rarer plants cost more to start.</li>
     *   <li>{@code S} — the world root (wheat seeds / nether wart / chorus flower).</li>
     *   <li>{@code B} — the resource itself, one item. Requiring the resource means
     *       a bush can only sustain a resource you have already obtained, never
     *       shortcut you to it.</li>
     * </ul>
     * Yields one seed: the plant then returns its seed when broken and keeps producing
     * until its lifetime limit, so the catalyst is the only true cost.
     */
    private void bushsSeedRecipeProvider() {
        for (ResourcePlantProfile profile : ResourcePlantProfile.values()) {
            Item core = resourceCore(profile);
            Item root = rootSeed(profile);
            Item catalyst = catalyst(profile);
            if (core == null || root == null || catalyst == null) continue;
            me.ez.orebushes.Common.Bushes.BushBlockItem seed = seedItem(profile);
            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, seed, 1)
                    .define('B', core)
                    .define('S', root)
                    .define('C', catalyst)
                    .pattern(" C ")
                    .pattern("SBS")
                    .pattern(" C ")
                    .unlockedBy("has_core", has(core))
                    .save(output);
        }
    }

    /** Non-refundable progression catalyst, one tier rarer for each plant tier. */
    private static Item catalyst(ResourcePlantProfile profile) {
        return switch (profile.tier) {
            case 1 -> Items.BONE_MEAL;
            case 2 -> Items.AMETHYST_SHARD;
            case 3 -> Items.ENDER_PEARL;
            default -> Items.ECHO_SHARD;
        };
    }

    /** The resource the plant produces; you must already own it to craft the seed. */
    private static Item resourceCore(ResourcePlantProfile profile) {
        return switch (profile) {
            case COAL -> Items.COAL;
            case IRON -> Items.RAW_IRON;
            case GOLD -> Items.RAW_GOLD;
            case EMERALD -> Items.EMERALD;
            case DIAMOND -> Items.DIAMOND;
            case REDSTONE -> Items.REDSTONE;
            case LAPIS -> Items.LAPIS_LAZULI;
            case COPPER -> Items.RAW_COPPER;
            case NETHERITE -> Items.NETHERITE_SCRAP;
            case GLOWSTONE -> Items.GLOWSTONE_DUST;
            case QUARTZ -> Items.QUARTZ;
            case AMETHYST -> Items.AMETHYST_SHARD;
            case EXPERIENCE -> Items.EXPERIENCE_BOTTLE;
            case ECHO_SHARD -> Items.ECHO_SHARD;
            case GOLDEN_APPLE -> Items.GOLDEN_APPLE;
            case SUGAR -> Items.SUGAR_CANE;
            case ANCIENT_DEBRIS -> Items.ANCIENT_DEBRIS;
            case BLAZE -> Items.BLAZE_ROD;
            case ENDER_PEARL -> Items.ENDER_PEARL;
            case ENDER_EYE -> Items.ENDER_EYE;
            case CHORUS -> Items.CHORUS_FRUIT;
            case SHULKER_SHELL -> Items.SHULKER_SHELL;
            case DRAGON_BREATH -> Items.DRAGON_BREATH;
        };
    }

    private static Item rootSeed(ResourcePlantProfile profile) {
        if (profile.ordinal() >= ResourcePlantProfile.ENDER_PEARL.ordinal()) return Items.CHORUS_FLOWER;
        if (profile.ordinal() >= ResourcePlantProfile.QUARTZ.ordinal()) return Items.NETHER_WART;
        return Items.WHEAT_SEEDS;
    }

    private static me.ez.orebushes.Common.Bushes.BushBlockItem seedItem(ResourcePlantProfile profile) {
        return (me.ez.orebushes.Common.Bushes.BushBlockItem) Init.ITEMS.getEntries().stream()
                .map(holder -> holder.get())
                .filter(item -> item instanceof me.ez.orebushes.Common.Bushes.BushBlockItem bush
                        && bush.getBlock() instanceof me.ez.orebushes.Common.Bushes.AbstractModBushBlock plant
                        && plant.profile() == profile)
                .findFirst().orElseThrow();
    }

    /** Runner so this provider can be registered with the data generator. */
    public static class Runner extends RecipeProvider.Runner {
        public Runner(net.minecraft.data.PackOutput output, java.util.concurrent.CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Ore Bushes Recipes";
        }
    }
}
