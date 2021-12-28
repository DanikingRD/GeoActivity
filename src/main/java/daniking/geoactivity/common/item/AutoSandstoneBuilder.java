package daniking.geoactivity.common.item;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.api.item.BuilderItem;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;

public class AutoSandstoneBuilder extends BuilderItem {
    public AutoSandstoneBuilder(Settings settings) {
        super(settings);
    }

    @Override
    public ItemScreenHandler getGui(int syncId, PlayerInventory playerInventory, GAInventory inventory) {
        return null;
    }

    @Override
    public int inventorySize() {
        return 3;
    }
}
