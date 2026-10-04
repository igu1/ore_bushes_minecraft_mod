package me.ez.orebushes.Datagen;

import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = Main.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent e){
        DataGenerator generator = e.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = e.getExistingFileHelper();

        generator.addProvider(e.includeClient(), new ItemModelProvider(output, helper));
        generator.addProvider(e.includeClient(), new BlockStateProvider(output, helper));
        generator.addProvider(e.includeClient(), new LanguageProvider(output, "en_us"));
        generator.addProvider(e.includeServer(), new ModRecipeProvider(output, e.getLookupProvider()));
        generator.addProvider(e.includeServer(), new LootTableProvider(output, e.getLookupProvider()));
    }
}
