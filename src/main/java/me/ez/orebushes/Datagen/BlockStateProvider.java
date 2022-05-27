package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeBush(Init.COAL_BUSH.get(), "coal_bush_stage", "coal_bush_stage");
        makeBush(Init.IRON_BUSH.get(), "iron_bush_stage", "iron_bush_stage");
        makeBush(Init.GOLD_BUSH.get(), "gold_bush_stage", "gold_bush_stage");
        makeBush(Init.EMERALD_BUSH.get(), "emerald_bush_stage", "emerald_bush_stage");
        makeBush(Init.REDSTONE_BUSH.get(), "redstone_bush_stage", "redstone_bush_stage");
        makeBush(Init.LAPIS_BUSH.get(), "lapis_bush_stage", "lapis_bush_stage");
        makeBush(Init.DIAMOND_BUSH.get(), "diamond_bush_stage", "diamond_bush_stage");

        makeBush(Init.COPPER_BUSH.get(), "copper_bush_stage", "copper_bush_stage");
        makeBush(Init.GLOWSTONE_BUSH.get(), "glowstone_bush_stage", "glowstone_bush_stage");
        makeBush(Init.QUARTZ_BUSH.get(), "quartz_bush_stage", "quartz_bush_stage");
        makeBush(Init.NETHERITE_BUSH.get(), "netherite_bush_stage", "netherite_bush_stage");


    }

    public void makeBush(BushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, BushBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(BlockStateProperties.AGE_3),
                new ResourceLocation(Main.MOD_ID, "block/" + textureName + state.getValue(BlockStateProperties.AGE_3))));
        return models;
    }
}
