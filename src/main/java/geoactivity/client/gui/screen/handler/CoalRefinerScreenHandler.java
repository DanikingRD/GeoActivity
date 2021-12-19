package geoactivity.client.gui.screen.handler;

import geoactivity.api.gui.handler.ScreenHandlerBase;
import geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class CoalRefinerScreenHandler extends ScreenHandlerBase {

    private final PropertyDelegate propertyDelegate;
    public CoalRefinerScreenHandler(int syncId, PlayerInventory playerInventory) {

        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4));
    }

    public CoalRefinerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(GAScreenHandlerTypes.COAL_REFINER, syncId, playerInventory, inventory);
        this.propertyDelegate = propertyDelegate;
        this.addProperties(this.propertyDelegate);
        this.builder()
                .playerSetup()
                .slot(0, 31, 36)
                .slot(1, 71, 36)
                .output(2, 128, 36)
                .build();
    }

    public int getCookProgress() {
        int i = this.propertyDelegate.get(2);
        int j = this.propertyDelegate.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    public int getFuelProgress() {
        int i = this.propertyDelegate.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.propertyDelegate.get(0) * 13 / i;
    }

    public boolean isBurning() {
        return this.propertyDelegate.get(0) > 0;
    }

}
