package geoactivity.client.gui.screen.handler;

import geoactivity.api.gui.handler.ItemScreenHandler;
import geoactivity.api.item.PerkItem;
import geoactivity.api.item.Rechargeable;
import geoactivity.common.item.util.GAChargeItem;
import geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class BasicUpgradeScreenHandler extends ItemScreenHandler {

    public BasicUpgradeScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2));
    }
    public BasicUpgradeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(GAScreenHandlerTypes.BASIC_UPGRADE, syncId, playerInventory, inventory);
        this.builder().playerSetup()
                .slot(Rechargeable.CHARGE_SLOT_INDEX, 80, 34, stack -> stack.getItem() instanceof GAChargeItem)
                .slot(1, 9, 9, stack -> stack.getItem() instanceof PerkItem)
                .build();
    }
}
