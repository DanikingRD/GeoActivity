package daniking.geoactivity.common.item;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.api.item.BuilderItem;
import daniking.geoactivity.client.gui.screen.handler.AutoStoneBuilderScreenHandler;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;

public class AutoStoneBuilderItem extends BuilderItem {

    public AutoStoneBuilderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemScreenHandler getGui(int syncId, PlayerInventory playerInventory, GAInventory inventory) {
        return new AutoStoneBuilderScreenHandler(syncId, playerInventory, inventory);
    }

    @Override
    public int size() {
        return 3;
    }
}
