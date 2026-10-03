package me.ez.orebushes;

import me.ez.orebushes.Common.Bushes.BushBlockItem;
import me.ez.orebushes.Events.VillagerTradeHandler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MOD_ID)
public class Main
{

    public static final String MOD_ID = "orebushes";

    public Main()
    {
        Init.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Init.BUSHES.register(FMLJavaModLoadingContext.get().getModEventBus());
        Init.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Init.BLOCK_ENTITY.register(FMLJavaModLoadingContext.get().getModEventBus());
        Init.MENUS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PlantEffects.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PlantEffects.PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
        WorldGen.CONFIGURED_FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        WorldGen.PLACED_FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(VillagerTradeHandler.class);
    }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void SetupClient(FMLClientSetupEvent e){
            Init.BUSHES.getEntries().forEach(bushes -> {
                ItemBlockRenderTypes.setRenderLayer(bushes.get(), RenderType.cutout());
            });
            ItemBlockRenderTypes.setRenderLayer(Init.BUSH_HARVESTER.get(), RenderType.cutout());
            MenuScreens.register(Init.BUSH_HARVESTER_MENU.get(), me.ez.orebushes.Client.BushHarvesterScreen::new);
        }

        @SubscribeEvent
        public static void CommonSetup(FMLCommonSetupEvent e){
            e.enqueueWork(() -> {
                if (!Config.ENABLE_COMPOSTING.get()) {
                    return;
                }
                float chance = Config.COMPOST_CHANCE.get().floatValue();
                Init.ITEMS.getEntries().forEach(item -> {
                    if (item.get() instanceof BushBlockItem) {
                        ComposterBlock.COMPOSTABLES.put(item.get(), chance);
                    }
                });
            });
        }
    }
}
