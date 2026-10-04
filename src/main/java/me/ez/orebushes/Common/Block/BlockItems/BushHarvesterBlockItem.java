package me.ez.orebushes.Common.Block.BlockItems;

import me.ez.orebushes.Init;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BushHarvesterBlockItem extends BlockItem {

    public BushHarvesterBlockItem() {
        super(Init.BUSH_HARVESTER.get(), new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("Right-click: 36-slot storage; overflow goes to the inventory above.")
                .withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("Shows its harvest radius while running. Redstone pauses it.")
                .withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("Blue idle • Green running • Red blocked • Amber paused")
                .withStyle(ChatFormatting.DARK_GRAY));
        components.add(Component.literal("Speed (block below): iron 30s • gold 20s • diamond 1s • 40s default")
                .withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("Range (2 below): iron 3 • gold 4 • diamond 8 • 2 default")
                .withStyle(ChatFormatting.GRAY));
    }
}
