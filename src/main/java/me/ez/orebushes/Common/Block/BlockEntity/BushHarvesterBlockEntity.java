package me.ez.orebushes.Common.Block.BlockEntity;

import me.ez.orebushes.Init;
import me.ez.orebushes.Util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;

public class BushHarvesterBlockEntity extends BlockEntity{

    public BushHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(Init.BUSH_HARVESTER_BLOCK_ENTITY.get(), pos, state);
    }

    public static <E extends BlockEntity> void Ticker(Level level, BlockPos pos, BlockState state, E e) {
        if (!level.isClientSide){
            try {
                if (level.getBlockEntity(pos.above()) != null){
                    BlockEntity blockEntity = level.getBlockEntity(pos.above());
                    blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                        InventoryUtil.addItemToInventory(handler, Items.EMERALD, 1);
                    });
                }
            } catch (Exception ex){
                System.out.println("Error Found: " + ex);
            }
        }
    }
}
