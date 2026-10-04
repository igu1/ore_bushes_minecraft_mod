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
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class BushHarvesterBlockEntity extends BlockEntity implements WorldlyContainer, MenuProvider {

    /** Spacious internal output inventory (4 rows of 9). */
    public static final int SLOTS = 36;
    private static final Logger LOGGER = LogUtils.getLogger();

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
    final ResourceHandler<ItemResource> itemHandler = new InventoryResourceHandler();

    private int tick;
    private int activeTicks;

    public BushHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(Init.BUSH_HARVESTER_BLOCK_ENTITY.get(), pos, state);
    }

    public static <E extends BlockEntity> void Ticker(Level level, BlockPos pos, BlockState state, E e) {
        if (level.isClientSide()) return;
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
        try (Transaction transaction = Transaction.openRoot()) {
            int inserted = itemHandler.insert(ItemResource.of(stack), stack.getCount(), transaction);
            ItemStack remainder = inserted >= stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - inserted);
            if (!remainder.isEmpty()) {
                BlockEntity above = level.getBlockEntity(pos.above());
                if (above != null) {
                    ResourceHandler<ItemResource> aboveHandler =
                            level.getCapability(Capabilities.Item.BLOCK, above.getBlockPos(), Direction.UP);
                    if (aboveHandler != null) {
                        int overflowInserted = aboveHandler.insert(ItemResource.of(remainder), remainder.getCount(), transaction);
                        if (overflowInserted >= remainder.getCount()) {
                            remainder = ItemStack.EMPTY;
                        } else {
                            remainder = remainder.copyWithCount(remainder.getCount() - overflowInserted);
                        }
                    }
                }
            }
            transaction.commit();
            return remainder;
        }
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
    protected void loadAdditional(net.minecraft.world.level.storage.ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, items);
    }

    @Override
    protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
    }

    // Drops the stored items when the harvester is broken (1.21.5+ split).
    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level != null) {
            net.minecraft.world.Containers.dropContents(level, pos, this);
        }
    }

    // Capability registration (NeoForge transfer API)

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, Init.BUSH_HARVESTER_BLOCK_ENTITY.get(),
                (be, side) -> be.itemHandler);
    }

    /** Minimal {@link ResourceHandler} exposing the internal {@link NonNullList} as items. */
    private final class InventoryResourceHandler implements ResourceHandler<ItemResource> {
        @Override
        public int size() {
            return SLOTS;
        }

        @Override
        public ItemResource getResource(int index) {
            return ItemResource.of(items.get(index));
        }

        @Override
        public long getAmountAsLong(int index) {
            return items.get(index).getCount();
        }

        @Override
        public long getCapacityAsLong(int index, ItemResource resource) {
            return resource.isEmpty() ? 0 : Math.min(resource.getItem().getDefaultMaxStackSize(), getMaxStackSize());
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return !resource.isEmpty();
        }

        @Override
        public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
            if (resource.isEmpty() || amount <= 0) return 0;
            ItemStack existing = items.get(index);
            int limit = Math.min(resource.getItem().getDefaultMaxStackSize(), getMaxStackSize());
            if (existing.isEmpty()) {
                int moved = Math.min(amount, limit);
                items.set(index, resource.toStack(moved));
                setChanged();
                return moved;
            }
            if (!ItemStack.isSameItemSameComponents(existing, resource.toStack(1))) return 0;
            int room = limit - existing.getCount();
            if (room <= 0) return 0;
            int moved = Math.min(amount, room);
            existing.grow(moved);
            setChanged();
            return moved;
        }

        @Override
        public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
            if (resource.isEmpty() || amount <= 0) return 0;
            ItemStack existing = items.get(index);
            if (existing.isEmpty() || !ItemStack.isSameItemSameComponents(existing, resource.toStack(1))) return 0;
            int moved = Math.min(amount, existing.getCount());
            existing.shrink(moved);
            if (existing.isEmpty()) items.set(index, ItemStack.EMPTY);
            setChanged();
            return moved;
        }
    }
}
