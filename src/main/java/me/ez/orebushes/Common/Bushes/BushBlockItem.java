package me.ez.orebushes.Common.Bushes;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BushBlockItem extends BlockItem {

    public BushBlockItem(Block p_40565_) {
        super(p_40565_, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }
}
