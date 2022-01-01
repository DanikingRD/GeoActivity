package daniking.geoactivity.client.gui.screen.handler;

import daniking.geoactivity.api.gui.handler.ScreenHandlerBase;
import daniking.geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class AdvancedCoalRefinerScreenHandler extends ScreenHandlerBase {

    private final PropertyDelegate delegate;

    public AdvancedCoalRefinerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(4));
    }

    public AdvancedCoalRefinerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(GAScreenHandlerTypes.ADVANCED_COAL_REFINER, syncId, playerInventory, inventory);
        this.delegate = delegate;
        addProperties(this.delegate);
        this.builder()
                .playerSetup()
                .slot(0, 42, 40)
                .slot(1,81, 22)
                .slot(2, 81, 57)
                .output(3, 120, 40)
                .output(4, 146, 40)
                .build();

    }

    public boolean isBurning() {
        return this.delegate.get(0) > 0;
    }

    public int getBurnTimeScaled(int scale) {
        if (this.delegate.get(0) == 0) {
            return 0;
        }
        return this.delegate.get(0) * scale / delegate.get(1);
    }
    public int getDoubleProgressScaled(int scale) {
        if (this.delegate.get(0) > 0) {
            return this.delegate.get(2) * scale / this.delegate.get(3);
        }
        return 0;
    }

}
