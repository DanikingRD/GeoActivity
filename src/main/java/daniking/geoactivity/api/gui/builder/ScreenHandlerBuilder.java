package daniking.geoactivity.api.gui.builder;

import daniking.geoactivity.api.gui.handler.ScreenHandlerBase;
import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.client.gui.screen.handler.slot.ChargeSlot;
import daniking.geoactivity.client.gui.screen.handler.slot.GAOutputSlot;
import daniking.geoactivity.client.gui.screen.handler.slot.GASlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.apache.commons.lang3.Range;

import java.util.function.Predicate;

public final class ScreenHandlerBuilder {

    private final ScreenHandlerBase parent;
    private final PlayerInventory playerInventory;
    private final Inventory inventory;
    private final PlayerEntity player;
    private int start;

    public ScreenHandlerBuilder(final ScreenHandlerBase parent) {
        this.parent = parent;
        this.playerInventory = parent.getPlayerInventory();
        this.inventory = parent.getInventory();
        this.player = playerInventory.player;
    }

    public ScreenHandlerBuilder slot(final int index, final int posX, final int posY) {
        return this.slot(index, posX, posY, always -> true);
    }

    public ScreenHandlerBuilder slot(final int index, final int posX, final int posY, final Predicate<ItemStack> canInsert) {
        this.parent.addSlot(new GASlot(this.inventory, index, posX, posY, canInsert));
        return this;
    }

    public ScreenHandlerBuilder output(final int index, final int posX, final int posY) {
        this.parent.addSlot(new GAOutputSlot(this.player, this.inventory, index, posX, posY));
        return this;
    }


    public ScreenHandlerBuilder charge(final int posX, final int posY) {
        this.parent.addSlot(new ChargeSlot(this.inventory, Rechargeable.CHARGE_SLOT_INDEX, posX, posY));
        return this;
    }

    public ScreenHandlerBuilder container3x3(final Inventory inventory) {
        return this.container3x3(inventory, 30, 17);
    }

    public ScreenHandlerBuilder container3x3(final Inventory inventory, final int posX, final int posY) {
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.parent.addSlot(new Slot(inventory, y + x * 3, posX + y * 18, posY + x * 18));
            }
        }
        return this;
    }

    public ScreenHandlerBuilder playerSetup() {
        return this.playerSetup(8, 84);
    }

    public ScreenHandlerBuilder playerSetup(int posX, int posY) {
        final int i = this.parent.slots.size();
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.parent.addSlot(new Slot(this.playerInventory, k + j * 9 + 9, posX + k * 18, posY + j * 18));
            }
        }
        posY += 58;
        for (int x = 0; x < 9; x++) {
            this.parent.addSlot(new Slot(this.playerInventory, x, posX + x * 18, posY));
        }
        final int j = this.parent.slots.size() - 1;
        this.parent.addPlayerRange(Range.between(i, j));
        this.start = this.parent.slots.size();
        return this;
    }

    /**
     * Builds the container slot range.
     * Should be called at the end of the builder
     */
    public void build() {
        this.parent.addContainerRange(Range.between(this.start, this.parent.slots.size() - 1));
    }
}
