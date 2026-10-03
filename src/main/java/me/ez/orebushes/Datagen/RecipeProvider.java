package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
        ShapedRecipeBuilder.shaped(Init.BUSH_HARVESTER_BLOCK_ITEM.get())
                .pattern("IHI").pattern("CRC").pattern("IPI")
                .define('I', Items.IRON_INGOT).define('H', Items.HOPPER)
                .define('C', Items.COPPER_INGOT).define('R', Items.REDSTONE)
                .define('P', Items.PISTON).unlockedBy("has_hopper", has(Items.HOPPER)).save(recipeConsumer);

        bushsSeedRecipeProvider(recipe -> recipeConsumer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(com.google.gson.JsonObject json) {
                recipe.serializeRecipeData(json);
                String item = json.getAsJsonObject("result").get("item").getAsString();
                me.ez.orebushes.Common.Bushes.ResourcePlantProfile profile = java.util.Arrays.stream(
                        me.ez.orebushes.Common.Bushes.ResourcePlantProfile.values())
                        .filter(p -> item.equals("orebushes:" + p.id + "_bush")).findFirst().orElseThrow();
                json.getAsJsonObject("result").addProperty("count", 1);
                com.google.gson.JsonArray pattern = new com.google.gson.JsonArray();
                pattern.add(" C ");
                pattern.add("SBS");
                pattern.add(" C ");
                json.add("pattern", pattern);
                com.google.gson.JsonObject catalyst = new com.google.gson.JsonObject();
                String ingredient = switch (profile.tier) {
                    case 1 -> "minecraft:bone_meal";
                    case 2 -> "minecraft:amethyst_shard";
                    case 3 -> "minecraft:ender_pearl";
                    default -> profile.id.equals("echo_shard") ? "minecraft:sculk_sensor" : "minecraft:echo_shard";
                };
                catalyst.addProperty("item", ingredient);
                json.getAsJsonObject("key").add("C", catalyst);
                com.google.gson.JsonObject resource = new com.google.gson.JsonObject();
                resource.addProperty("item", switch (profile) {
                    case COAL -> "minecraft:charcoal";
                    case IRON -> "minecraft:iron_nugget";
                    case GOLD -> "minecraft:gold_nugget";
                    case EMERALD -> "orebushes:emerald_nugget";
                    case DIAMOND -> "orebushes:diamond_nugget";
                    case COPPER -> "orebushes:copper_nugget";
                    case NETHERITE -> "orebushes:netherite_nugget";
                    case REDSTONE -> "minecraft:redstone";
                    case LAPIS -> "minecraft:lapis_lazuli";
                    case AMETHYST -> "minecraft:amethyst_shard";
                    case EXPERIENCE -> "minecraft:glass_bottle";
                    case ECHO_SHARD -> "minecraft:sculk_catalyst";
                    case GOLDEN_APPLE -> "minecraft:apple";
                    case SUGAR -> "minecraft:sugar_cane";
                    case QUARTZ -> "minecraft:quartz";
                    case GLOWSTONE -> "minecraft:glowstone_dust";
                    case ANCIENT_DEBRIS -> "minecraft:netherite_scrap";
                    case BLAZE -> "minecraft:blaze_powder";
                    case ENDER_PEARL -> "minecraft:popped_chorus_fruit";
                    case ENDER_EYE -> "minecraft:ender_eye";
                    case CHORUS -> "minecraft:chorus_fruit";
                    case SHULKER_SHELL -> "minecraft:purpur_block";
                    case DRAGON_BREATH -> "minecraft:dragon_breath";
                });
                json.getAsJsonObject("key").add("B", resource);
                com.google.gson.JsonObject root = new com.google.gson.JsonObject();
                root.addProperty("item", profile.ordinal() >= me.ez.orebushes.Common.Bushes.ResourcePlantProfile.ENDER_PEARL.ordinal()
                        ? "minecraft:chorus_flower" : profile.ordinal() >= me.ez.orebushes.Common.Bushes.ResourcePlantProfile.QUARTZ.ordinal()
                        ? "minecraft:nether_wart" : "minecraft:wheat_seeds");
                json.getAsJsonObject("key").add("S", root);
            }

            @Override
            public net.minecraft.resources.ResourceLocation getId() { return recipe.getId(); }
            @Override
            public net.minecraft.world.item.crafting.RecipeSerializer<?> getType() { return recipe.getType(); }
            @Override
            public com.google.gson.JsonObject serializeAdvancement() { return recipe.serializeAdvancement(); }
            @Override
            public net.minecraft.resources.ResourceLocation getAdvancementId() { return recipe.getAdvancementId(); }
        }));

        //Items

        ShapelessRecipeBuilder
                .shapeless(Items.DIAMOND)
                .requires(Init.DIAMOND_NUGGET.get(), 9)
                .unlockedBy("has_diamond_nugget",
                        has(Init.DIAMOND_NUGGET.get()))
                .save(recipeConsumer);


        ShapelessRecipeBuilder
                .shapeless(Items.NETHERITE_INGOT)
                .requires(Init.NETHERITE_NUGGET.get(), 9)
                .unlockedBy("has_netherite_nugget",
                        has(Init.NETHERITE_NUGGET.get()))
                .save(recipeConsumer);


        ShapelessRecipeBuilder
                .shapeless(Items.COPPER_INGOT)
                .requires(Init.COPPER_NUGGET.get(), 9)
                .unlockedBy("has_copper_nugget",
                        has(Init.COPPER_NUGGET.get()))
                .save(recipeConsumer);


        ShapelessRecipeBuilder
                .shapeless(Items.EMERALD)
                .requires(Init.EMERALD_NUGGET.get(), 9)
                .unlockedBy("has_emerald_nugget",
                        has(Items.EMERALD))
                .save(recipeConsumer);

        //Ingots To Nuggets
        ShapelessRecipeBuilder
                .shapeless(Init.EMERALD_NUGGET.get(), 9)
                .requires(Items.EMERALD)
                .unlockedBy("has_emerald",
                        has(Items.EMERALD))
                .save(recipeConsumer);

        ShapelessRecipeBuilder
                .shapeless(Init.DIAMOND_NUGGET.get(), 9)
                .requires(Items.DIAMOND)
                .unlockedBy("has_diamond",
                        has(Items.DIAMOND))
                .save(recipeConsumer);

        ShapelessRecipeBuilder
                .shapeless(Init.COPPER_NUGGET.get(), 9)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot",
                        has(Items.DIAMOND))
                .save(recipeConsumer);

        ShapelessRecipeBuilder
                .shapeless(Init.NETHERITE_NUGGET.get(), 9)
                .requires(Items.NETHERITE_INGOT)
                .unlockedBy("has_netherite_ingot",
                        has(Items.NETHERITE_INGOT))
                .save(recipeConsumer);
    }

    private void bushsSeedRecipeProvider(Consumer<FinishedRecipe> recipeConsumer) {
        ShapedRecipeBuilder
                .shaped(Init.COAL_BUSH_ITEM.get(), 2)
                .define('B', Items.COAL_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_coal_block",
                        has(Items.COAL_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.IRON_BUSH_ITEM.get(), 2)
                .define('B', Items.IRON_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_iron_block",
                        has(Items.IRON_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.GOLD_BUSH_ITEM.get(), 2)
                .define('B', Items.GOLD_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_gold_block",
                        has(Items.GOLD_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.EMERALD_BUSH_ITEM.get(), 2)
                .define('B', Items.EMERALD_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_emerald_block",
                        has(Items.EMERALD_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.DIAMOND_BUSH_ITEM.get(), 2)
                .define('B', Items.DIAMOND_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_diamond_block",
                        has(Items.DIAMOND_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.REDSTONE_BUSH_ITEM.get(), 2)
                .define('B', Items.REDSTONE_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_redstone_block",
                        has(Items.REDSTONE_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.LAPIS_BUSH_ITEM.get(), 2)
                .define('B', Items.LAPIS_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_lapiz_block",
                        has(Items.LAPIS_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.COPPER_BUSH_ITEM.get(), 2)
                .define('B', Items.COPPER_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_copper_block",
                        has(Items.COPPER_BLOCK))
                .save(recipeConsumer);


        ShapedRecipeBuilder
                .shaped(Init.NETHERITE_BUSH_ITEM.get(), 2)
                .define('B', Items.NETHERITE_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_netherite_block",
                        has(Items.NETHERITE_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.GLOWSTONE_BUSH_ITEM.get(), 2)
                .define('B', Items.GLOWSTONE)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_glowstone_block",
                        has(Items.GLOWSTONE))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.QUARTZ_BUSH_ITEM.get(), 2)
                .define('B', Items.QUARTZ_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_quartz_block",
                        has(Items.QUARTZ_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.AMETHYST_BUSH_ITEM.get(), 2)
                .define('B', Items.AMETHYST_BLOCK)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_amethyst_block",
                        has(Items.AMETHYST_BLOCK))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.EXPERIENCE_BUSH_ITEM.get(), 2)
                .define('B', Items.EXPERIENCE_BOTTLE)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_experience_bottle",
                        has(Items.EXPERIENCE_BOTTLE))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.ECHO_SHARD_BUSH_ITEM.get(), 2)
                .define('B', Items.ECHO_SHARD)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_echo_shard",
                        has(Items.ECHO_SHARD))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.GOLDEN_APPLE_BUSH_ITEM.get(), 2)
                .define('B', Items.GOLDEN_APPLE)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_golden_apple",
                        has(Items.GOLDEN_APPLE))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.SUGAR_BUSH_ITEM.get(), 2)
                .define('B', Items.SUGAR_CANE)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_sugar_cane",
                        has(Items.SUGAR_CANE))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.ANCIENT_DEBRIS_BUSH_ITEM.get(), 2)
                .define('B', Items.ANCIENT_DEBRIS)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_ancient_debris",
                        has(Items.ANCIENT_DEBRIS))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.BLAZE_BUSH_ITEM.get(), 2)
                .define('B', Items.BLAZE_ROD)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_blaze_rod",
                        has(Items.BLAZE_ROD))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.ENDER_PEARL_BUSH_ITEM.get(), 2)
                .define('B', Items.ENDER_PEARL)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_ender_pearl",
                        has(Items.ENDER_PEARL))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.ENDER_EYE_BUSH_ITEM.get(), 2)
                .define('B', Items.ENDER_EYE)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_ender_eye",
                        has(Items.ENDER_EYE))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.CHORUS_BUSH_ITEM.get(), 2)
                .define('B', Items.CHORUS_FRUIT)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_chorus_fruit",
                        has(Items.CHORUS_FRUIT))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.SHULKER_SHELL_BUSH_ITEM.get(), 2)
                .define('B', Items.SHULKER_SHELL)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_shulker_shell",
                        has(Items.SHULKER_SHELL))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Init.DRAGON_BREATH_BUSH_ITEM.get(), 2)
                .define('B', Items.DRAGON_BREATH)
                .define('S', Items.WHEAT_SEEDS)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" B ")
                .unlockedBy("has_dragon_breath",
                        has(Items.DRAGON_BREATH))
                .save(recipeConsumer);

    }
}
