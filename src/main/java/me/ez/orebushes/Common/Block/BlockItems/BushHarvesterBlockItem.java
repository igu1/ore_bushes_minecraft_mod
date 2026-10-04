package me.ez.orebushes.Common.Block.BlockItems;

import me.ez.orebushes.Init;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BushHarvesterBlockItem extends BlockItem {

    public BushHarvesterBlockItem(Item.Properties properties) {
        super(Init.BUSH_HARVESTER.get(), properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, java.util.function.Consumer<Component> adder, TooltipFlag flag) {
        adder.accept(Component.literal("Right-click: 36-slot storage; overflow goes to the inventory above.")
                .withStyle(ChatFormatting.AQUA));
        adder.accept(Component.literal("Shows its harvest radius while running. Redstone pauses it.")
                .withStyle(ChatFormatting.GRAY));
        adder.accept(Component.literal("Blue idle • Green running • Red blocked • Amber paused")
                .withStyle(ChatFormatting.DARK_GRAY));
        adder.accept(Component.literal("Speed (block below): iron 30s • gold 20s • diamond 1s • 40s default")
                .withStyle(ChatFormatting.GRAY));
        adder.accept(Component.literal("Range (2 below): iron 3 • gold 4 • diamond 8 • 2 default")
                .withStyle(ChatFormatting.GRAY));
    }
}
