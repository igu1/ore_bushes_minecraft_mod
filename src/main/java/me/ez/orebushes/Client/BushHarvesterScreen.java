package me.ez.orebushes.Client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import me.ez.orebushes.Common.Menu.BushHarvesterMenu;
import me.ez.orebushes.Main;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/** Ore Harvester themed screen: fully graphical, no text labels. */
public class BushHarvesterScreen extends AbstractContainerScreen<BushHarvesterMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "textures/gui/bushharvester.png");

    public BushHarvesterScreen(BushHarvesterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = BushHarvesterMenu.IMAGE_WIDTH;
        this.imageHeight = BushHarvesterMenu.IMAGE_HEIGHT;
        // Suppress all vanilla text: the theme is conveyed by the background art only.
        this.titleLabelY = 10_000;
        this.inventoryLabelY = 10_000;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // This is a standalone image, not a 256x256 vanilla GUI atlas.
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F,
                imageWidth, imageHeight, imageWidth, imageHeight);
    }

    /** Intentionally draws nothing so the UI stays text-free. */
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }
}
