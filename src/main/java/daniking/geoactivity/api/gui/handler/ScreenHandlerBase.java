package daniking.geoactivity.api.gui.handler;

import daniking.geoactivity.api.gui.builder.ScreenHandlerBuilder;
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

    protected boolean transferItem(final ItemStack stack, final int start, final int end) {

        if (stack.isEmpty()) {
            return false;
        }
        final int stackCount = stack.getCount();

        if (stackCount <= 0) {
            return false;
        }
        //case where the container is empty
        for (int i = start; i < end; i++) {
            final Slot slotAt = this.getSlot(i);//container slots
            final ItemStack stackInSlot = slotAt.getStack();
            if (stackInSlot.isEmpty() && slotAt.canInsert(stack)) {
                final int maxCount = Math.min(stack.getMaxCount(), slotAt.getMaxItemCount());
                final int insertCount = Math.min(maxCount, stack.getCount());
                final ItemStack moveStack = stack.copy();
                moveStack.setCount(insertCount);
                slotAt.setStack(moveStack);
                stack.decrement(insertCount);
            }
        }

        //case where there container is not empty
        for (int i = start; i < end; i++) {
            final Slot slotAt = this.getSlot(i);//container slots
            final ItemStack stackInSlot = slotAt.getStack();
            if (slotAt.canInsert(stack) && ItemStack.areItemsEqual(stackInSlot, stack)) {
                //what is the max count for the stack
                final int maxCount = Math.min(stack.getMaxCount(), slotAt.getMaxItemCount());
                final int remainingSpace = (maxCount - stackInSlot.getCount());
                if (remainingSpace > 0) {
                    final int insertCount = Math.min(remainingSpace, stackInSlot.getCount());
                    stackInSlot.increment(insertCount);
                    stack.decrement(insertCount);
                }
            }
        }

        //If we moved some, but still have more left over lets try again
        if (!stack.isEmpty() && stack.getCount() != stackCount) {
            transferItem(stack, start, end);
        }
        return stack.getCount() != stackCount;//means we successfully inserted the stack
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
