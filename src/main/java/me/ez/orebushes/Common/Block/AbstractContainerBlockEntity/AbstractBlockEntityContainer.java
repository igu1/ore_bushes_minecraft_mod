package me.ez.orebushes.Common.Block.AbstractContainerBlockEntity;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBlockEntityContainer extends AbstractContainerMenu {

    private final BlockEntity blockEntity;
    private final Level level;
    private final Block block;
    private final int SIZE;

    public AbstractBlockEntityContainer(@Nullable MenuType<?> type,
                                        int windowId,
                                        BlockEntity blockEntity,
                                        Block block,
                                        int size) {
        super(type, windowId);
        this.blockEntity = blockEntity;
        this.level = blockEntity.getLevel();
        this.block = block;
        this.SIZE = size;
    }

    public BlockEntity getBlockEntity() {
        return blockEntity;
    }

    public Level getLevel() {
        return level;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, block);
    }

    public void playerInventory(Inventory inventory) {
        for(int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(inventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
            }
        }
    }

    public void playerHotBar(Inventory inventory) {
        for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(inventory, j1, 8 + j1 * 18, 142));
        }
    }

    public ItemStack getNormalMoveStack(Player player, int index, int SIZE){
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int BB_CONTAINER_SIZE = SIZE;
            if (index < BB_CONTAINER_SIZE) {
                if (!this.moveItemStackTo(itemstack1, BB_CONTAINER_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, BB_CONTAINER_SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}
