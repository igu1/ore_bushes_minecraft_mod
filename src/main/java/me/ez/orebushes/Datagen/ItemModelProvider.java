package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        simpleItem(Init.COAL_BUSH_ITEM.get());
        simpleItem(Init.IRON_BUSH_ITEM.get());
        simpleItem(Init.GOLD_BUSH_ITEM.get());
        simpleItem(Init.EMERALD_BUSH_ITEM.get());
        simpleItem(Init.REDSTONE_BUSH_ITEM.get());
        simpleItem(Init.LAPIS_BUSH_ITEM.get());
        simpleItem(Init.DIAMOND_BUSH_ITEM.get());
        simpleItem(Init.QUARTZ_BUSH_ITEM.get());
        simpleItem(Init.GLOWSTONE_BUSH_ITEM.get());

        simpleItem(Init.DIAMOND_NUGGET.get());
        simpleItem(Init.EMERALD_NUGGET.get());
    }

    private ItemModelBuilder simpleItem(Item item){
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Main.MOD_ID ,"item/" + item.getRegistryName().getPath()));
    }
}
