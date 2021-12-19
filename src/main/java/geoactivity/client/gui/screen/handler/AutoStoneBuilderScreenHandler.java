package geoactivity.client.gui.screen.handler;

import geoactivity.api.gui.handler.ItemScreenHandler;
import geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class AutoStoneBuilderScreenHandler extends ItemScreenHandler {

    public AutoStoneBuilderScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    public AutoStoneBuilderScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(GAScreenHandlerTypes.AUTO_STONE_BUILDER, syncId, playerInventory, inventory);
        this.builder()
                .playerSetup()
                .charge(80, 25)
                .slot(1, 53,49, ItemScreenHandler::canInsert)
                .slot(2, 107,49, ItemScreenHandler::canInsert)
                .build();

    }
}
