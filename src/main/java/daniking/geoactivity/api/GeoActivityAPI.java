package daniking.geoactivity.api;

import daniking.geoactivity.api.item.Charge;
import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.registry.GATags;
import daniking.geoactivity.common.util.GAInventory;
import daniking.geoactivity.common.util.NbtHelper;
import daniking.geoactivity.common.util.RechargeableHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GeoActivityAPI {

    /**
     * Charges a rechargeable item.
     * @param container ItemStack.
     */
    public static void charge(final ItemStack container) {
        if (container.getItem() instanceof Rechargeable rechargeable) {
            final GAInventory inventory = GAInventory.create(container, rechargeable.size());
            if (!inventory.isEmpty()) {
                final int chargeSlotIndex = Rechargeable.CHARGE_SLOT_INDEX;
                final ItemStack fuelStack = inventory.getStack(chargeSlotIndex);
                if (fuelStack.isIn(GATags.CHARGE)) {
                    int charge = 0;
                    for (final Item chargeItem : GATags.CHARGE.values()) {
                        if (fuelStack.getItem() == chargeItem) {
                            if (chargeItem instanceof final Charge fuel) {
                                charge = fuel.getCharge();
                            }
                        }
                    }
                    if (charge != 0) {
                        final int damage = container.getDamage();
                        final int restored = (damage - charge);
                        if (restored > 0) {
                            container.setDamage(restored);
                            inventory.removeStack(chargeSlotIndex, 1);
                        }
                        if (container.getDamage() != damage) {
                            if (RechargeableHelper.isDestroyed(container)) {
                                NbtHelper.remove(container, Rechargeable.DESTROYED_NBT_KEY);
                            }
                        }
                    }
                }
            }
        }
    }
}
