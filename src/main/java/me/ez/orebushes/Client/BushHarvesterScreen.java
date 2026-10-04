package me.ez.orebushes.Client;

import me.ez.orebushes.Common.Menu.BushHarvesterMenu;
import me.ez.orebushes.Main;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/** Ore Harvester themed screen: fully graphical, no text labels. */
@OnlyIn(Dist.CLIENT)
public class BushHarvesterScreen extends AbstractContainerScreen<BushHarvesterMenu> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Main.MOD_ID, "textures/gui/bushharvester.png");

    public BushHarvesterScreen(BushHarvesterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, BushHarvesterMenu.IMAGE_WIDTH, BushHarvesterMenu.IMAGE_HEIGHT);
        // Suppress all vanilla text: the theme is conveyed by the background art only.
        this.titleLabelY = 10_000;
        this.inventoryLabelY = 10_000;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // This is a standalone image, not a 256x256 vanilla GUI atlas.
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F,
                imageWidth, imageHeight, imageWidth, imageHeight);
    }

    /** Intentionally draws nothing so the UI stays text-free. */
    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
    }
}
