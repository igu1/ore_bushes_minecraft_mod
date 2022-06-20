package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
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

        simpleItem(Init.DIAMOND_NUGGET);
        simpleItem(Init.EMERALD_NUGGET);
        simpleItem(Init.COPPER_NUGGET);
        simpleItem(Init.NETHERITE_NUGGET);
    }

    private <T extends Item> ItemModelBuilder simpleItem(RegistryObject<T> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Main.MOD_ID ,"item/" + item.getId().getPath()));
    }
}
