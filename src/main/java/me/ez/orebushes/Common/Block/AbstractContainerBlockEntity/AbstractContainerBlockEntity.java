package me.ez.orebushes.Common.Block.AbstractContainerBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractContainerBlockEntity extends BaseContainerBlockEntity {


    private final String default_name;
    private final int container_size;

    private NonNullList<ItemStack> itemStacks;
    private IItemHandler handler;

    private final LazyOptional<?> itemHandler;

    public AbstractContainerBlockEntity(BlockEntityType<?> type,
                                        BlockPos pos,
                                        BlockState state,
                                        String default_name,
                                        int container_size) {
        super(type, pos, state);
        this.default_name = default_name;
        this.container_size = container_size;
        this.itemStacks = NonNullList.withSize(this.container_size, ItemStack.EMPTY);
        this.handler = new InvWrapper(this);
        this.itemHandler = LazyOptional.of(() -> this.handler);
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return super.createMenu(i, inventory, player);
    }

    public IItemHandler getHandler() {
        return handler;
    }

    public NonNullList<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public LazyOptional<?> getItemHandler() {
        return itemHandler;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal(this.default_name);
    }

    @Override
    protected abstract AbstractContainerMenu createMenu(int windowid, Inventory inventory);

    @Override
    public int getContainerSize() {
        return this.container_size;
    }

    @Override
    public boolean isEmpty() {
        for(int x = 0; x < this.container_size; x++){
            if (!handler.getStackInSlot(x).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int i) {
        return itemStacks.get(i);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(itemStacks, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(itemStacks, i);
    }

    @Override
    public void setItem(int i, ItemStack stack) {
        itemStacks.set(i, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clearContent() {
        itemStacks.clear();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, itemStacks);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, itemStacks);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap);
    }


}
