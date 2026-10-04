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

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.DIAMOND)
                .requires(Init.DIAMOND_NUGGET.get(), 9)
                .unlockedBy("has_diamond_nugget", has(Init.DIAMOND_NUGGET.get())).save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.NETHERITE_INGOT)
                .requires(Init.NETHERITE_NUGGET.get(), 9)
                .unlockedBy("has_netherite_nugget", has(Init.NETHERITE_NUGGET.get())).save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.COPPER_INGOT)
                .requires(Init.COPPER_NUGGET.get(), 9)
                .unlockedBy("has_copper_nugget", has(Init.COPPER_NUGGET.get())).save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.EMERALD)
                .requires(Init.EMERALD_NUGGET.get(), 9)
                .unlockedBy("has_emerald_nugget", has(Items.EMERALD)).save(output);

        //Ingots To Nuggets
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Init.EMERALD_NUGGET.get(), 9)
                .requires(Items.EMERALD).unlockedBy("has_emerald", has(Items.EMERALD)).save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Init.DIAMOND_NUGGET.get(), 9)
                .requires(Items.DIAMOND).unlockedBy("has_diamond", has(Items.DIAMOND)).save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Init.COPPER_NUGGET.get(), 9)
                .requires(Items.COPPER_INGOT).unlockedBy("has_copper_ingot", has(Items.DIAMOND)).save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Init.NETHERITE_NUGGET.get(), 9)
                .requires(Items.NETHERITE_INGOT).unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(output);

        bushsSeedRecipeProvider();
    }

    /** Shape " B " / "SBS" / " B " where B is the resource block and S the starter seed. */
    private void bushsSeedRecipeProvider() {
        for (ResourcePlantProfile profile : ResourcePlantProfile.values()) {
            Item block = resourceBlock(profile);
            Item root = rootSeed(profile);
            if (block == null || root == null) continue;
            me.ez.orebushes.Common.Bushes.BushBlockItem seed = seedItem(profile);
            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, seed, 2)
                    .define('B', block)
                    .define('S', root)
                    .pattern(" B ")
                    .pattern(" S ")
                    .pattern(" B ")
                    .unlockedBy("has_block", has(block))
                    .save(output);
        }
    }

    private static Item resourceBlock(ResourcePlantProfile profile) {
        return switch (profile) {
            case COAL -> Items.COAL_BLOCK;
            case IRON -> Items.IRON_BLOCK;
            case GOLD -> Items.GOLD_BLOCK;
            case EMERALD -> Items.EMERALD_BLOCK;
            case DIAMOND -> Items.DIAMOND_BLOCK;
            case REDSTONE -> Items.REDSTONE_BLOCK;
            case LAPIS -> Items.LAPIS_BLOCK;
            case COPPER -> Items.COPPER_BLOCK;
            case NETHERITE -> Items.NETHERITE_BLOCK;
            case GLOWSTONE -> Items.GLOWSTONE;
            case QUARTZ -> Items.QUARTZ_BLOCK;
            case AMETHYST -> Items.AMETHYST_BLOCK;
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
