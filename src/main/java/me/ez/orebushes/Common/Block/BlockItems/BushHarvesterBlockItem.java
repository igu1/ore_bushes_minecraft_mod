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

    public BushHarvesterBlockItem() {
        super(Init.BUSH_HARVESTER.get(), new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, java.util.function.Consumer<Component> adder, TooltipFlag flag) {
        adder.accept(Component.literal("Right-click to open the 36-slot harvester storage.").withStyle(ChatFormatting.AQUA));
        adder.accept(Component.literal("Fills its own storage, then any inventory above.").withStyle(ChatFormatting.GRAY));
        adder.accept(Component.literal("Shows its harvesting radius while working. Redstone pauses.").withStyle(ChatFormatting.GRAY));
        adder.accept(Component.literal("Blue idle • Green running • Red blocked • Amber paused").withStyle(ChatFormatting.DARK_GRAY));
        adder.accept(Component.literal("Speed (block below): iron 30s, gold 20s, diamond 1s, default 40s.").withStyle(ChatFormatting.GRAY));
        adder.accept(Component.literal("Range (2 below): iron 3m, gold 4m, diamond 8m, default 2m.").withStyle(ChatFormatting.GRAY));
    }
}
