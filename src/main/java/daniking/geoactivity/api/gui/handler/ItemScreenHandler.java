package daniking.geoactivity.api.gui.handler;

import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import org.apache.commons.lang3.Range;

public class ItemScreenHandler extends ScreenHandlerBase {

    protected GAInventory inventory;

    protected ItemScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, GAInventory inventory) {
        super(type, syncId, playerInventory, inventory);
        this.inventory = inventory;
        this.inventory.onOpen(playerInventory.player);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public static boolean isNotRechargeable(final ItemStack stack) {
        return !(stack.getItem() instanceof Rechargeable);
    }

    @Override
    public void onSlotClick(int i, int j, SlotActionType type, PlayerEntity player) {
        for (Range<Integer> range : this.playerRanges) {
            if (range.contains(i)) {
                System.out.println(range);
                if (this.getSlot(i).getStack() == this.inventory.getContainer()) {
                    return;
                }
            }
        }
        super.onSlotClick(i, j, type, player);
    }
}
