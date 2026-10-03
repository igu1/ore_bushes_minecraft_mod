package me.ez.orebushes.Common.Menu;

import me.ez.orebushes.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BushHarvesterMenu extends AbstractContainerMenu {

    public static final int MACHINE_SLOTS = 36;
    // Shared by the screen and the GUI texture generator.
    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 184;
    public static final int SLOT_X = 8;
    public static final int SLOT_SPACING = 18;
    public static final int OUTPUT_Y = 18;
    public static final int INVENTORY_Y = 103;
    public static final int HOTBAR_Y = 161;
    private static final int PLAYER_INVENTORY_START = 9;

    private final Container container;

    public BushHarvesterMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, resolve(inventory, data));
    }

    private static Container resolve(Inventory inventory, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();
        return inventory.player.level.getBlockEntity(pos) instanceof Container container
                ? container : new SimpleContainer(MACHINE_SLOTS);
    }

    public BushHarvesterMenu(int id, Inventory inventory, Container container) {
        super(Init.BUSH_HARVESTER_MENU.get(), id);
        this.container = container;

        // 4 rows of 9 internal output slots.
        for (int row = 0; row < MACHINE_SLOTS / 9; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(container, column + row * 9, SLOT_X + column * SLOT_SPACING, OUTPUT_Y + row * SLOT_SPACING));
            }
        }
        // Player inventory.
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + row * 9 + PLAYER_INVENTORY_START, SLOT_X + column * SLOT_SPACING, INVENTORY_Y + row * SLOT_SPACING));
            }
        }
        // Hotbar.
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, SLOT_X + column * SLOT_SPACING, HOTBAR_Y));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        if (index < MACHINE_SLOTS) {
            if (!moveItemStackTo(stack, MACHINE_SLOTS, slots.size(), true)) return ItemStack.EMPTY;
        } else if (!moveItemStackTo(stack, 0, MACHINE_SLOTS, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return original;
    }
}
