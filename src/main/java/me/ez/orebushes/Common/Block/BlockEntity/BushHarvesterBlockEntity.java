package me.ez.orebushes.Common.Block.BlockEntity;

import com.mojang.logging.LogUtils;
import me.ez.orebushes.Common.Block.BushHarvester;
import me.ez.orebushes.Common.Block.BushHarvester.OperatingState;
import me.ez.orebushes.Common.Bushes.AbstractModBushBlock;
import me.ez.orebushes.Common.Menu.BushHarvesterMenu;
import me.ez.orebushes.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class BushHarvesterBlockEntity extends BlockEntity implements WorldlyContainer, MenuProvider {

    /** Spacious internal output inventory (4 rows of 9). */
    public static final int SLOTS = 36;
    private static final Logger LOGGER = LogUtils.getLogger();

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
    final IItemHandler itemHandler = new InvWrapper(this);

    private int tick;
    private int activeTicks;

    public BushHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(Init.BUSH_HARVESTER_BLOCK_ENTITY.get(), pos, state);
    }

    public static <E extends BlockEntity> void Ticker(Level level, BlockPos pos, BlockState state, E e) {
        if (level.isClientSide) return;
        BushHarvesterBlockEntity harvester = (BushHarvesterBlockEntity) e;

        if (level.hasNeighborSignal(pos)) {
            harvester.tick = 0;
            harvester.activeTicks = 0;
            setMode(level, pos, OperatingState.PAUSED);
            return;
        }
        if (harvester.activeTicks > 0) {
            harvester.activeTicks--;
            if (harvester.activeTicks == 0) setMode(level, pos, OperatingState.IDLE);
        }
        if (state.getValue(BushHarvester.MODE) == OperatingState.PAUSED) setMode(level, pos, OperatingState.IDLE);

        harvester.tick++;
        if (harvester.tick > BushHarvester.harvestSpeed(level, pos) * 20) {
            harvester.tick = 0;
            try {
                OperatingState mode = harvester.work(level, pos);
                setMode(level, pos, mode);
                if (mode == OperatingState.RUNNING) harvester.activeTicks = 40;
            } catch (Exception ex) {
                LOGGER.error("Ore Harvester failed to harvest at {}", pos, ex);
            }
        }
    }

    private OperatingState work(Level level, BlockPos pos) {
        int range = BushHarvester.harvestRange(level, pos);
        OperatingState mode = OperatingState.IDLE;
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                BlockPos position = pos.offset(x, 0, z);
                BlockState blockState = level.getBlockState(position);
                if (!(blockState.getBlock() instanceof AbstractModBushBlock plant)) continue;

                ItemStack drop = plant.harvestDrop(blockState);
                if (drop.isEmpty()) continue;

                ItemStack remainder = insertOutput(level, pos, drop);
                if (remainder.getCount() < drop.getCount()) {
                    plant.finishHarvest((ServerLevel) level, position, blockState, false);
                    mode = OperatingState.RUNNING;
                    if (!remainder.isEmpty()) Block.popResource(level, position, remainder);
                } else if (mode != OperatingState.RUNNING) {
                    mode = OperatingState.BLOCKED;
                }
            }
        }
        return mode;
    }

    /** Fills the internal inventory first, then an item handler directly above as overflow. */
    private ItemStack insertOutput(Level level, BlockPos pos, ItemStack stack) {
        ItemStack remainder = ItemHandlerHelper.insertItemStacked(itemHandler, stack.copy(), false);
        if (!remainder.isEmpty()) {
            BlockEntity above = level.getBlockEntity(pos.above());
            if (above != null) {
                IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, above.getBlockPos(), null);
                if (handler != null) {
                    remainder = ItemHandlerHelper.insertItemStacked(handler, remainder, false);
                }
            }
        }
        return remainder;
    }

    private static void setMode(Level level, BlockPos pos, OperatingState mode) {
        BlockState state = level.getBlockState(pos);
        if (state.hasProperty(BushHarvester.MODE) && state.getValue(BushHarvester.MODE) != mode) {
            level.setBlock(pos, state.setValue(BushHarvester.MODE, mode), 3);
        }
    }

    // WorldlyContainer / Container

    @Override
    public int getContainerSize() {
        return SLOTS;
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(items, slot, amount);
        if (!stack.isEmpty()) setChanged();
        return stack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return level != null && level.getBlockEntity(worldPosition) == this
                && player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        int[] slots = new int[SLOTS];
        for (int i = 0; i < SLOTS; i++) slots[i] = i;
        return slots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    // MenuProvider

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.orebushes.bushharvester");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BushHarvesterMenu(id, inventory, this);
    }

    // Persistence and capability

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    // Capability registration (NeoForge 1.20.2+ style)

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, Init.BUSH_HARVESTER_BLOCK_ENTITY.get(),
                (be, side) -> be.itemHandler);
    }
}
