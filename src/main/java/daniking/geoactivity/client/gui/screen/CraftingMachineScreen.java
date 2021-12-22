package daniking.geoactivity.client.gui.screen;

import daniking.geoactivity.api.gui.GuiBase;
import daniking.geoactivity.client.gui.screen.handler.CraftingMachineScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CraftingMachineScreen extends GuiBase<CraftingMachineScreenHandler> {

    public CraftingMachineScreen(CraftingMachineScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawContainer3x3(matrices, this.left() + 8, this.top() + 16);
        this.builder().drawSlot(matrices, this.left() + 70, this.top() + 52);
        this.builder().drawSlot(matrices, this.left() + 110, this.top() + 52);
        this.builder().drawSlot(matrices, this.left() + 90, this.top() + 16);
        this.builder().drawOutputSlot(matrices, this.left() + 142, this.top() + 30);
      //  this.builder().drawBurningProgress(this, matrices, handler.getFuelProgress(), 23,this.left() + 92, this.top() + 54, mouseX, mouseY);
        this.builder().drawBigProgressBar(matrices, this.left() + 64, this.top() + 36, handler.getCookProgress(), mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        this.titleY = 6;
        this.titleX = 8;
    }
}
