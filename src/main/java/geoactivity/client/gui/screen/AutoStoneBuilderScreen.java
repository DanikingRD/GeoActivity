package geoactivity.client.gui.screen;

import geoactivity.api.gui.GuiBase;
import geoactivity.client.gui.screen.handler.AutoStoneBuilderScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class AutoStoneBuilderScreen extends GuiBase<AutoStoneBuilderScreenHandler> {

    public AutoStoneBuilderScreen(AutoStoneBuilderScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawSlot(matrices, this.left() + 79, this.top() + 24);
        this.builder().drawSlot(matrices, this.left() + 52, this.top() + 48);
        this.builder().drawSlot(matrices, this.left() + 106, this.top() + 48);
        this.builder().drawProgress(matrices, this.left() + 77, this.top() + 48, 0, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX += 3;
        this.titleY = 10;
    }
}
