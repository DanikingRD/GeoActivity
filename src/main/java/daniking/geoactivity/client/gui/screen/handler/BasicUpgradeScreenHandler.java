package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.api.item.PerkItem;
import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.item.util.GAChargeItem;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class BasicUpgradeScreenHandler extends ItemScreenHandler {

    public BasicUpgradeScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, GAInventory.create(ItemStack.EMPTY, 2));
    }
    public BasicUpgradeScreenHandler(int syncId, PlayerInventory playerInventory, GAInventory inventory) {
        super(GAScreenHandlerTypes.BASIC_UPGRADE, syncId, playerInventory, inventory);
        this.builder().playerSetup()
                .slot(Rechargeable.CHARGE_SLOT_INDEX, 80, 34, stack -> stack.getItem() instanceof GAChargeItem)
                .slot(1, 9, 9, stack -> stack.getItem() instanceof PerkItem)
                .build();
    }
}
