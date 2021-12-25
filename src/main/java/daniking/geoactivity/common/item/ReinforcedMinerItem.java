package daniking.geoactivity.common.item;

import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.api.item.MinerItem;
import daniking.geoactivity.client.gui.screen.handler.ReinforcedMinerScreenHandler;
import daniking.geoactivity.common.util.GAInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ToolMaterial;

public class ReinforcedMinerItem extends MinerItem {

    public ReinforcedMinerItem(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings) {
        super(attackDamage, attackSpeed, material, settings);
    }

    @Override
    public int inventorySize() {
        return 1;
    }

    @Override
    public ItemScreenHandler getGui(int syncId, PlayerInventory playerInventory, GAInventory inventory) {
        return new ReinforcedMinerScreenHandler(syncId, playerInventory, inventory);
    }
}
