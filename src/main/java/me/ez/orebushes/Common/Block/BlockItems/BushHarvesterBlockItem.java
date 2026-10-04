package me.ez.orebushes.Common.Block.BlockItems;

import me.ez.orebushes.Init;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
        if (Screen.hasShiftDown()) {
            adder.accept(Component.translatable("Hint: Place A Chest Top Of The Harvester").withStyle(ChatFormatting.AQUA));
            adder.accept(Component.translatable("Speed:"));
            adder.accept(Component.translatable(" Place: \n" +
                    "  Iron Block: 30s\n" +
                    "  Gold Block: 20s\n" +
                    "  Diamond Block: 1s\n" +
                    "  Default: 40s").withStyle(ChatFormatting.GRAY));
            adder.accept(Component.translatable("""
                    Place at Y= -1 position of Harvester
                    or under the Harvester.
                    """).withStyle(ChatFormatting.DARK_GRAY));
            adder.accept(Component.translatable("Range:"));
            adder.accept(Component.translatable(" Place:\n" +
                    "  Iron Block: 3m\n" +
                    "  Gold Block: 4m\n" +
                    "  Diamond Block: 8m\n" +
                    "  Default: 2m").withStyle(ChatFormatting.GRAY));
            adder.accept(Component.translatable("""
                    Place at Y= -2 position of Harvester
                    or under the block you placed for speed.
                    """).withStyle(ChatFormatting.DARK_GRAY));
        }else {
            adder.accept(Component.translatable("Hold Shift For More Information").withStyle(ChatFormatting.GRAY));
        }
    }
}
