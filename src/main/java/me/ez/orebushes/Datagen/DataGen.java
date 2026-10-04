package me.ez.orebushes.Datagen;

import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MOD_ID)
public class DataGen {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent e){
        DataGenerator generator = e.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(e.includeDev(), new LanguageProvider(output, "en_us"));
        generator.addProvider(e.includeDev(), new ModRecipeProvider.Runner(output, e.getLookupProvider()));
        generator.addProvider(e.includeDev(), new LootTableProvider(output, e.getLookupProvider()));
    }
}
