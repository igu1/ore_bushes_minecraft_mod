package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        simpleItem(Init.COAL_BUSH_ITEM);
        simpleItem(Init.IRON_BUSH_ITEM);
        simpleItem(Init.GOLD_BUSH_ITEM);
        simpleItem(Init.EMERALD_BUSH_ITEM);
        simpleItem(Init.REDSTONE_BUSH_ITEM);
        simpleItem(Init.LAPIS_BUSH_ITEM);
        simpleItem(Init.DIAMOND_BUSH_ITEM);
        simpleItem(Init.COPPER_BUSH_ITEM);

        simpleItem(Init.QUARTZ_BUSH_ITEM);
        simpleItem(Init.GLOWSTONE_BUSH_ITEM);
        simpleItem(Init.NETHERITE_BUSH_ITEM);

        simpleItem(Init.AMETHYST_BUSH_ITEM);
        simpleItem(Init.EXPERIENCE_BUSH_ITEM);
        simpleItem(Init.ECHO_SHARD_BUSH_ITEM);
        simpleItem(Init.GOLDEN_APPLE_BUSH_ITEM);
        simpleItem(Init.SUGAR_BUSH_ITEM);
        simpleItem(Init.ANCIENT_DEBRIS_BUSH_ITEM);
        simpleItem(Init.BLAZE_BUSH_ITEM);

        simpleItem(Init.ENDER_PEARL_BUSH_ITEM);
        simpleItem(Init.ENDER_EYE_BUSH_ITEM);
        simpleItem(Init.CHORUS_BUSH_ITEM);
        simpleItem(Init.SHULKER_SHELL_BUSH_ITEM);
        simpleItem(Init.DRAGON_BREATH_BUSH_ITEM);
    }

    private <T extends Item> ItemModelBuilder simpleItem(DeferredHolder<Item, T> item){
        if (item.get() instanceof me.ez.orebushes.Common.Bushes.BushBlockItem plant) {
            String id = me.ez.orebushes.Common.Bushes.ResourcePlantProfile.of(plant.getBlock()).id;
            return withExistingParent(item.getId().getPath(), modLoc("block/plants/" + id + "_v2_1"));
        }
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "item/" + item.getId().getPath()));
    }
}
