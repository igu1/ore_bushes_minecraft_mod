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
        super(Init.BUSH_HARVESTER.get(),new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("Hint: Place A Chest Top Of The Harvester").withStyle(ChatFormatting.AQUA));
            components.add(Component.translatable("Speed:"));
            components.add(Component.translatable(" Place: \n" +
                    "  Iron Block: 30s\n" +
                    "  Gold Block: 20s\n" +
                    "  Diamond Block: 1s\n" +
                    "  Default: 40s").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("""
                    Place at Y= -1 position of Harvester
                    or under the Harvester.
                    """).withStyle(ChatFormatting.DARK_GRAY));
            components.add(Component.translatable("Range:"));
                    components.add(Component.translatable(" Place:\n" +
                            "  Iron Block: 3m\n" +
                            "  Gold Block: 4m\n" +
                            "  Diamond Block: 8m\n" +
                            "  Default: 2m").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("""
                    Place at Y= -2 position of Harvester
                    or under the block you placed for speed.
                    """).withStyle(ChatFormatting.DARK_GRAY));
        }else {
            components.add(Component.translatable("Hold Shift For More Information").withStyle(ChatFormatting.GRAY));
        }
    }
}
