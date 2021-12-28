package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class ReinforcedMinerScreenHandler extends ItemScreenHandler {

    public ReinforcedMinerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, GAInventory.create(ItemStack.EMPTY, 1));
    }
    public ReinforcedMinerScreenHandler(int syncId, PlayerInventory playerInventory, GAInventory inventory) {
        super(GAScreenHandlerTypes.REINFORCED_MINER, syncId, playerInventory, inventory);
        this.builder()
                .playerSetup()
                .charge(80, 34)
                .build();
    }
}
