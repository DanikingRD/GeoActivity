package geoactivity.client.gui.screen;

import geoactivity.api.gui.GuiBase;
import geoactivity.client.gui.screen.handler.ReinforcedMinerScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ReinforcedMinerScreen extends GuiBase<ReinforcedMinerScreenHandler> {

    public ReinforcedMinerScreen(ReinforcedMinerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawSlot(matrices, this.left() + 79, this.top() + 33);
    }
}
