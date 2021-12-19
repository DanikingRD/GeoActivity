package geoactivity.api.gui.handler;

import geoactivity.api.item.Rechargeable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;

public class ItemScreenHandler extends ScreenHandlerBase {

    private final Inventory inventory;

    protected ItemScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId, playerInventory, inventory);
        this.inventory = inventory;
        this.inventory.onOpen(playerInventory.player);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public static boolean canInsert(final ItemStack stack) {
        return !(stack.getItem() instanceof Rechargeable);
    }
}
