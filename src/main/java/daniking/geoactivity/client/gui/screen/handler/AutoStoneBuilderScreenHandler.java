package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.client.gui.screen.handler.slot.GASlot;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class AutoStoneBuilderScreenHandler extends ItemScreenHandler {

    public AutoStoneBuilderScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, GAInventory.create(ItemStack.EMPTY, 3));
    }

    public AutoStoneBuilderScreenHandler(int syncId, PlayerInventory playerInventory, GAInventory inventory) {
        super(GAScreenHandlerTypes.AUTO_STONE_BUILDER, syncId, playerInventory, inventory);
        this.builder()
                .playerSetup()
                .charge(80, 23)
                .slot(1, 53,44, ItemScreenHandler::isNotRechargeable)
                .customSlot(new GASlot(this.inventory, 2, 107, 44, ItemScreenHandler::isNotRechargeable) {
                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }
                }).build();

    }
}
