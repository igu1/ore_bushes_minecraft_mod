package me.ez.orebushes.Datagen;

import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent e){
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();

        generator.addProvider(true, new ItemModelProvider(generator, helper));
        generator.addProvider(true, new BlockStateProvider(generator, helper));
        generator.addProvider(true, new LanguageProvider(generator, "en_us"));
        generator.addProvider(true, new RecipeProvider(generator));
        generator.addProvider(true, new LootTableProvider(generator));
    }
}
