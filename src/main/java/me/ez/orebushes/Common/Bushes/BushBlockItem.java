package me.ez.orebushes.Common.Bushes;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import java.util.List;

public class BushBlockItem extends BlockItem {

    public BushBlockItem(Block block) {
        super(block, new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        ResourcePlantProfile profile = ResourcePlantProfile.of(getBlock());
        tooltip.add(Component.literal(profile.conditions).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Tier " + profile.tier + " • " + profile.harvestLimit() + " harvests, then spent")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Harvest when ripe; needs its substrate in the right dimension.")
                .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.literal("Breaking a living plant returns the seed; a spent plant is gone.")
                .withStyle(ChatFormatting.DARK_GRAY));
        if (profile.tier > 1) tooltip.add(Component.literal("Cannot be bone-mealed").withStyle(ChatFormatting.DARK_GRAY));
    }
}
