package me.ez.orebushes;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
public class Main
{

    public static final String MOD_ID = "orebushes";

    public Main()
    {
        Init.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Init.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void SetupClient(FMLClientSetupEvent e){
            ItemBlockRenderTypes.setRenderLayer(Init.COAL_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.IRON_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.GOLD_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.REDSTONE_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.EMERALD_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.LAPIS_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.QUARTZ_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.GLOWSTONE_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.DIAMOND_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.COPPER_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Init.NETHERITE_BUSH.get(), RenderType.cutout());
        }
    }
}
