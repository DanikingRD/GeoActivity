package daniking.geoactivity.client.gui.screen;

import daniking.geoactivity.api.gui.GuiBase;
import daniking.geoactivity.api.gui.builder.ScreenBuilder;
import daniking.geoactivity.client.gui.screen.handler.BasicUpgradeScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class BasicUpgradeScreen extends GuiBase<BasicUpgradeScreenHandler> {

    public BasicUpgradeScreen(BasicUpgradeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawSlot(matrices, this.left() + 79, this.top() + 33, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawSlot(matrices, this.left() + 8, this.top() + 8, ScreenBuilder.SlotType.DEFAULT);
    }

    @Override
    protected void init() {
        super.init();
    }
}
