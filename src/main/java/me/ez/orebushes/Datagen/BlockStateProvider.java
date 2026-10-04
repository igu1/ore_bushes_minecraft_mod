package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Main;
import me.ez.orebushes.Common.Bushes.AbstractModBushBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Main.MOD_ID, exFileHelper);
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

        makeBush(Init.AMETHYST_BUSH.get(), "amethyst_bush_stage", "amethyst_bush_stage");
        makeBush(Init.EXPERIENCE_BUSH.get(), "experience_bush_stage", "experience_bush_stage");
        makeBush(Init.ECHO_SHARD_BUSH.get(), "echo_shard_bush_stage", "echo_shard_bush_stage");
        makeBush(Init.GOLDEN_APPLE_BUSH.get(), "golden_apple_bush_stage", "golden_apple_bush_stage");
        makeBush(Init.SUGAR_BUSH.get(), "sugar_bush_stage", "sugar_bush_stage");

        makeBush(Init.ANCIENT_DEBRIS_BUSH.get(), "ancient_debris_bush_stage", "ancient_debris_bush_stage");
        makeBush(Init.BLAZE_BUSH.get(), "blaze_bush_stage", "blaze_bush_stage");

        makeBush(Init.ENDER_PEARL_BUSH.get(), "ender_pearl_bush_stage", "ender_pearl_bush_stage");
        makeBush(Init.ENDER_EYE_BUSH.get(), "ender_eye_bush_stage", "ender_eye_bush_stage");
        makeBush(Init.CHORUS_BUSH.get(), "chorus_bush_stage", "chorus_bush_stage");
        makeBush(Init.SHULKER_SHELL_BUSH.get(), "shulker_shell_bush_stage", "shulker_shell_bush_stage");
        makeBush(Init.DRAGON_BREATH_BUSH.get(), "dragon_breath_bush_stage", "dragon_breath_bush_stage");


    }

    public void makeBush(BushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, BushBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        AbstractModBushBlock plant = (AbstractModBushBlock) block;
        String stage = plant.isExhausted(state) ? "spent" : String.valueOf(state.getValue(BlockStateProperties.AGE_3));
        models[0] = new ConfiguredModel(models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "block/plants/" + plant.profile().id + "_v2_" + stage)));
        return models;
    }
}
