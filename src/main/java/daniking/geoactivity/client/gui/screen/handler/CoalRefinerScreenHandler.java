package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ScreenHandlerBase;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
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
    public boolean isBurning() {
        return this.propertyDelegate.get(0) > 0;
    }

    public int getBurnTimeScaled(int scale) {
        if (this.propertyDelegate.get(0) == 0) {
            return 0;
        }
        return this.propertyDelegate.get(0) * scale / propertyDelegate.get(1);
    }
    public int getProgressScaled(int scale) {
        if (this.propertyDelegate.get(0) > 0) {
            return this.propertyDelegate.get(2) * scale / this.propertyDelegate.get(3);
        }
        return 0;
    }


}
