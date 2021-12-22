package daniking.geoactivity.client.gui.screen;

import daniking.geoactivity.api.gui.GuiBase;
import daniking.geoactivity.client.gui.screen.handler.CoalRefinerScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CoalRefinerScreen extends GuiBase<CoalRefinerScreenHandler> {

    public CoalRefinerScreen(CoalRefinerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        final int slotPosY = this.top() + 35;
        this.builder().drawSlot(matrices, this.left() + 30, slotPosY);
        this.builder().drawSlot(matrices, this.left() + 70, slotPosY);
        this.builder().drawOutputSlot(matrices, this.left() + 123, slotPosY - 4);
        this.builder().drawSmeltingProgress(this, matrices, this.left() + 93, slotPosY + 1, handler.getProgressScaled(100), 100,  mouseX,  mouseY);
        this.builder().drawBurningProgress(this, matrices, handler.isBurning(), handler.getBurnTimeScaled(100), 100,this.left() + 51, slotPosY + 2,  mouseX, mouseY);
        this.builder().drawREIButton(matrices, this.left() + 129, slotPosY + 26);
    }
}
