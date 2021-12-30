package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ScreenHandlerBase;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class AdvancedCoalRefinerScreenHandler extends ScreenHandlerBase {

    public AdvancedCoalRefinerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(7));
    }

    public AdvancedCoalRefinerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(GAScreenHandlerTypes.ADVANCED_COAL_REFINER, syncId, playerInventory, inventory);
        this.builder()
                .playerSetup()
                .slot(0, 42, 40)
                .slot(1,81, 22)
                .slot(2, 81, 57)
                .output(3, 120, 40)
                .output(4, 146, 40)
                .build();
    }
}
