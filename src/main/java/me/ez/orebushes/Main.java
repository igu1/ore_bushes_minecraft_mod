package me.ez.orebushes;

import me.ez.orebushes.Common.Bushes.BushBlockItem;
import me.ez.orebushes.Common.Block.BlockEntity.BushHarvesterBlockEntity;
import me.ez.orebushes.Events.VillagerTradeHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Main.MOD_ID)
public class Main
{

    public static final String MOD_ID = "orebushes";

    public Main(IEventBus modEventBus, ModContainer modContainer)
    {
        // Blocks must register before items: the seed items wrap block DeferredHolders.
        Init.BUSHES.register(modEventBus);
        Init.BLOCKS.register(modEventBus);
        Init.BLOCK_ENTITY.register(modEventBus);
        Init.MENUS.register(modEventBus);
        Init.CREATIVE_TABS.register(modEventBus);
        Init.ITEMS.register(modEventBus);
        PlantEffects.SOUNDS.register(modEventBus);
        PlantEffects.PARTICLES.register(modEventBus);
        WorldGen.CONFIGURED_FEATURES.register(modEventBus);
        WorldGen.PLACED_FEATURES.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(BushHarvesterBlockEntity::registerCapabilities);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        NeoForge.EVENT_BUS.register(VillagerTradeHandler.class);
    }

    /** Adds the seeds and harvester to the vanilla creative tabs (1.19.3+ style). */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            Init.BUSHES.getEntries().forEach(h -> event.accept(h.get()));
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(Init.BUSH_HARVESTER_BLOCK_ITEM.get());
        }
    }


    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void registerScreens(net.neoforged.neoforge.client.event.RegisterMenuScreensEvent event) {
            event.register(Init.BUSH_HARVESTER_MENU.get(), me.ez.orebushes.Client.BushHarvesterScreen::new);
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
