package me.ez.orebushes.Common.Block.AbstractContainerBlockEntity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractBlockEntityScreen
        <T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    private final ResourceLocation CONTAINER_TEXTURE;

    public AbstractBlockEntityScreen(T generatorContainer,
                                     Inventory inventory,
                                     Component component,
                                     @Nullable String MOD_ID,
                                     String PATH_TO_TEXTURE_GUI) {
        super(generatorContainer, inventory, component);
        ++this.imageHeight;
        String mod_id = MOD_ID != null ? MOD_ID : "minecraft";
        this.CONTAINER_TEXTURE = new ResourceLocation(mod_id, PATH_TO_TEXTURE_GUI);
    }

    public void render(PoseStack poseStack, int p_99250_, int p_99251_, float p_99252_) {
        this.renderBackground(poseStack);
        super.render(poseStack, p_99250_, p_99251_, p_99252_);
        this.renderTooltip(poseStack, p_99250_, p_99251_);
    }

    @Override
    protected void renderBg(PoseStack stack, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
