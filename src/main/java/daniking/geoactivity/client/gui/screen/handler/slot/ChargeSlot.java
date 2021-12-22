package daniking.geoactivity.client.gui.screen.handler.slot;

import daniking.geoactivity.common.registry.GATags;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ChargeSlot extends GASlot {

    public ChargeSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y, stack -> true);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return GATags.CHARGE.contains(stack.getItem());
    }
}
