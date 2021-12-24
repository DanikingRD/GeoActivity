package daniking.geoactivity.client.gui.screen;

import daniking.geoactivity.api.gui.GuiBase;
import daniking.geoactivity.api.gui.builder.ScreenBuilder;
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
        this.builder().drawContainer3x3(matrices, this.left() + 29, this.top() + 16, ScreenBuilder.SlotType.DIAMOND);
        this.builder().drawOutputSlot(matrices, this.left() + 119, this.top() + 30, ScreenBuilder.SlotType.DIAMOND);
        this.builder().drawREIButton(matrices, this.left() + 125, this.top() + 60);
        this.builder().drawSmeltingProgress(this, matrices, this.left() + 90, this.top() + 35, this.handler.getProgressScaled(100), 100, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = 29;
        this.titleY = 6;
    }
}
