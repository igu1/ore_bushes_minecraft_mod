package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
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


        //Items
        ShapedRecipeBuilder
                .shaped(Items.DIAMOND)
                .define('#', Init.DIAMOND_NUGGET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_diamond_nugget",
                        has(Init.DIAMOND_NUGGET.get()))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Items.NETHERITE_INGOT)
                .define('#', Init.NETHERITE_NUGGET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_netherite_nugget",
                        has(Init.NETHERITE_NUGGET.get()))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Items.COPPER_INGOT)
                .define('#', Init.COPPER_NUGGET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_copper_nugget",
                        has(Init.COPPER_NUGGET.get()))
                .save(recipeConsumer);

        ShapedRecipeBuilder
                .shaped(Items.EMERALD)
                .define('#', Init.EMERALD_NUGGET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_emerald_nugget",
                        has(Init.EMERALD_NUGGET.get()))
                .save(recipeConsumer);



    }
}
