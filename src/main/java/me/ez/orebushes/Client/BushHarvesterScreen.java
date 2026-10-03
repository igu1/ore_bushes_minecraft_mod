package me.ez.orebushes.Client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import me.ez.orebushes.Common.Menu.BushHarvesterMenu;
import me.ez.orebushes.Main;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/** Ore Harvester themed screen: fully graphical, no text labels. */
@OnlyIn(Dist.CLIENT)
public class BushHarvesterScreen extends AbstractContainerScreen<BushHarvesterMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Main.MOD_ID, "textures/gui/bushharvester.png");

    public BushHarvesterScreen(BushHarvesterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = BushHarvesterMenu.IMAGE_WIDTH;
        this.imageHeight = BushHarvesterMenu.IMAGE_HEIGHT;
        // Suppress all vanilla text: the theme is conveyed by the background art only.
        this.titleLabelY = 10_000;
        this.inventoryLabelY = 10_000;
    }

    @Override
    public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
        renderBackground(pose);
        super.render(pose, mouseX, mouseY, partialTick);
        renderTooltip(pose, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        // This is a standalone image, not a 256x256 vanilla GUI atlas.
        blit(pose, leftPos, topPos, 0.0F, 0.0F, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    /** Intentionally draws nothing so the UI stays text-free. */
    @Override
    protected void renderLabels(PoseStack pose, int mouseX, int mouseY) {
    }
}
