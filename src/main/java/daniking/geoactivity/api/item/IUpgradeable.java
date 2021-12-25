package daniking.geoactivity.api.item;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.client.gui.screen.handler.BasicUpgradeScreenHandler;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;

public interface IUpgradeable extends Rechargeable {

    @Override
    default ItemScreenHandler getGui(final int syncId, final PlayerInventory playerInventory, final GAInventory inventory) {
        return new BasicUpgradeScreenHandler(syncId, playerInventory, inventory);
    }

    @Override
    default int inventorySize() {
        return 2;
    }
}
