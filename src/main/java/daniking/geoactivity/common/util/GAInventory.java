package daniking.geoactivity.common.util;

import daniking.geoactivity.api.misc.InventoryBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;

public final class GAInventory extends InventoryBase {

    private final ItemStack stack;

    public GAInventory(final ItemStack stack, final int size) {
        super(size);
        this.stack = stack;
        this.readNbt();
    }

    @Override
    public void onClose(PlayerEntity player) {
        super.onClose(player);
        this.writeNbt();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        this.writeNbt();
    }

    public void writeNbt() {
        Inventories.writeNbt(this.stack.getOrCreateSubNbt("Items"), this.getInventory());
    }

    public void readNbt() {
        Inventories.readNbt(this.stack.getOrCreateSubNbt("Items"), this.getInventory());
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return !stack.isEmpty();
    }

    public static GAInventory create(final ItemStack stack, final int size) {
        return new GAInventory(stack, size);
    }
}
