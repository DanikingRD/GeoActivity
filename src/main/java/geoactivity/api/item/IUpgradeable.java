package geoactivity.api.item;

import geoactivity.api.gui.handler.ItemScreenHandler;
import geoactivity.client.gui.screen.handler.BasicUpgradeScreenHandler;
import geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;

public interface IUpgradeable extends Rechargeable {

    @Override
    default ItemScreenHandler getGui(final int syncId, final PlayerInventory playerInventory, final GAInventory inventory) {
        return new BasicUpgradeScreenHandler(syncId, playerInventory, inventory);
    }

    @Override
    default int size() {
        return 2;
    }
}
