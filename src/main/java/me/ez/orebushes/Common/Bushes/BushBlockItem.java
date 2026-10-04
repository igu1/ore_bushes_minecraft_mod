package me.ez.orebushes.Common.Bushes;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import java.util.List;

public class BushBlockItem extends BlockItem {

    public BushBlockItem(Block block) {
        super(block, new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, java.util.function.Consumer<Component> adder, TooltipFlag flag) {
        ResourcePlantProfile profile = ResourcePlantProfile.of(getBlock());
        adder.accept(Component.literal(profile.conditions).withStyle(ChatFormatting.GRAY));
        adder.accept(Component.literal("Tier " + profile.tier + " • " + profile.harvestLimit() + " harvests, then spent")
                .withStyle(ChatFormatting.GOLD));
        adder.accept(Component.literal("Harvest when ripe. Breaking returns the seed.").withStyle(ChatFormatting.DARK_GRAY));
        if (profile.tier > 1) adder.accept(Component.literal("Cannot be bone-mealed").withStyle(ChatFormatting.DARK_GRAY));
    }
}
