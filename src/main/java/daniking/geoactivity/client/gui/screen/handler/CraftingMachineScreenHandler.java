package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ScreenHandlerBase;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class CraftingMachineScreenHandler extends ScreenHandlerBase {

    private final PropertyDelegate propertyDelegate;

    public CraftingMachineScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(10) , new ArrayPropertyDelegate(4));
    }

    public CraftingMachineScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(GAScreenHandlerTypes.CRAFTING_MACHINE, syncId, playerInventory, inventory);
        this.propertyDelegate = propertyDelegate;
        this.builder()
                .playerSetup()
                .container3x3(inventory, 30, 17)
                .output(9, 124, 35)
                .build();
        this.addProperties(this.propertyDelegate);
    }

    public int getProgressScaled(int scale) {
        if (this.propertyDelegate.get(1) > 0) {
            return this.propertyDelegate.get(0) * scale / this.propertyDelegate.get(1);
        }
        return 0;
    }
}
