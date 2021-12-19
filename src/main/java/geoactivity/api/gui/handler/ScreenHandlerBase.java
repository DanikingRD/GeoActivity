package geoactivity.api.gui.handler;

import geoactivity.api.gui.handler.builder.ScreenHandlerBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Base screen handler object for all containers of the mod
 */
public abstract class ScreenHandlerBase extends ScreenHandler {

    protected final PlayerInventory playerInventory;
    protected final Inventory inventory;
    protected final PlayerEntity player;
    protected final World world;
    protected final List<Range<Integer>> playerRanges;
    protected final List<Range<Integer>> containerRanges;

    protected ScreenHandlerBase(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);
        this.playerInventory = playerInventory;
        this.inventory = inventory;
        this.player = playerInventory.player;
        this.world = player.world;
        this.playerRanges = new ArrayList<>();
        this.containerRanges = new ArrayList<>();
    }

    protected ScreenHandlerBuilder builder() {
        return new ScreenHandlerBuilder(this);
    }

    @Override
    public ItemStack transferSlot(final PlayerEntity player, final int index) {
        ItemStack originalStack = ItemStack.EMPTY;
        final Slot slot = this.getSlot(index);
        if (slot != null && slot.hasStack()) {
            final ItemStack stackInSlot = slot.getStack();
            originalStack = stackInSlot.copy();
            boolean shifted = false;
            for (final Range<Integer> range : this.playerRanges) {
                if (range.contains(index)) {
                    if (this.shiftToBlockEntity(stackInSlot)) {
                        shifted = true;
                    }
                    break;
                }
            }
            if (!shifted) {
                for (final Range<Integer> range : this.containerRanges) {
                    if (range.contains(index)) {
                        if (this.shiftToPlayer(stackInSlot)) {
                            shifted = true;
                        }
                        break;
                    }
                }
            }
            slot.onQuickTransfer(stackInSlot, originalStack);
            if (stackInSlot.getCount() <= 0) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (stackInSlot.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, stackInSlot);
        }
        return originalStack;
    }

    private boolean shiftToBlockEntity(final ItemStack stackToShift) {
        if (this.inventory == null) {
            return false;
        }
        for (final Range<Integer> range : this.containerRanges) {
            if (this.transferItem(stackToShift, range.getMinimum(), range.getMaximum() + 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean shiftToPlayer(final ItemStack stackToShift) {
        for (final Range<Integer> range : this.playerRanges) {
            if (this.transferItem(stackToShift, range.getMinimum(), range.getMaximum() + 1)) {
                return true;
            }
        }
        return false;
    }

    protected boolean transferItem(final ItemStack stackToShift, final int start, final int end) {
        if (stackToShift.isEmpty()) {
            return false;
        }
        int stackCount = stackToShift.getCount();

        if (stackCount <= 0) {
            return false;
        }
        for (int i = start; i < end; i++) {
            final Slot slot = this.getSlot(i);
            final ItemStack stackInSlot = slot.getStack();
            int maxCount = Math.min(stackToShift.getMaxCount(), slot.getMaxItemCount());
            if (!stackToShift.isEmpty() && slot.canInsert(stackToShift)) {
                if (ItemStack.areItemsEqual(stackInSlot, stackToShift)) {
                    int freeStackSpace = maxCount - stackInSlot.getCount();
                    if (freeStackSpace > 0) {
                        int transferAmount = Math.min(freeStackSpace, stackToShift.getCount());
                        stackInSlot.increment(transferAmount);
                        stackToShift.decrement(transferAmount);
                    }
                }
            }
        }
        for (int i = start; i < end; i++) {
            final Slot slot = this.slots.get(i);
            final ItemStack stackInSlot = slot.getStack();
            if (stackInSlot.isEmpty() && slot.canInsert(stackToShift)) {
                int maxCount = Math.min(stackToShift.getMaxCount(), slot.getMaxItemCount());
                int moveCount = Math.min(maxCount, stackToShift.getCount());
                ItemStack moveStack = stackToShift.copy();
                moveStack.setCount(moveCount);
                slot.setStack(moveStack);
                stackToShift.decrement(moveCount);
            }
        }
        //If we moved some, but still have more left over lets try again
        if (!stackToShift.isEmpty() && stackToShift.getCount() != stackCount) {
            transferItem(stackToShift, start, end);
        }
        return stackToShift.getCount() != stackCount;
    }


    public void addContainerRange(final Range<Integer> range) {
        this.containerRanges.add(range);
    }

    public void addPlayerRange(final Range<Integer> range) {
        this.playerRanges.add(range);
    }

    @Override
    public Slot addSlot(Slot slot) {
        return super.addSlot(slot);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public World getWorld() {
        return world;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
