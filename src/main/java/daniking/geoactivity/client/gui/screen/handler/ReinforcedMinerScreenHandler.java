package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class ReinforcedMinerScreenHandler extends ItemScreenHandler {

    public ReinforcedMinerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }
    public ReinforcedMinerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(GAScreenHandlerTypes.REINFORCED_MINER, syncId, playerInventory, inventory);
        this.builder().playerSetup().charge(80, 34).build();
    }
}
