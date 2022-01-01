package daniking.geoactivity.client.gui.screen;

import daniking.geoactivity.api.gui.GuiBase;
import daniking.geoactivity.api.gui.builder.ScreenBuilder;
import daniking.geoactivity.client.gui.screen.handler.AdvancedCoalRefinerScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class AdvancedCoalRefinerScreen extends GuiBase<AdvancedCoalRefinerScreenHandler> {

    public AdvancedCoalRefinerScreen(AdvancedCoalRefinerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawSlot(matrices, this.left() + 41, this.top() + 39, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawSlot(matrices,  this.left() + 80, this.top() + 21, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawSlot(matrices, this.left() + 80, this.top() + 56, ScreenBuilder.SlotType.DEFAULT);
        //ouputs
        this.builder().drawOutputSlot(matrices, this.left() + 115, this.top() + 35, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawOutputSlot(matrices, this.left() + 141, this.top() + 35, ScreenBuilder.SlotType.DEFAULT);
        //perks
        this.builder().drawSlot(matrices, this.left() + 8, this.top() + 14, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawSlot(matrices, this.left() + 8, this.top() + 34, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawDoubleSmeltingProgress(this, matrices, this.left() + 90, this.top() + 40, handler.getDoubleProgressScaled(100), 100, mouseX, mouseY);
        this.builder().drawBurningProgress(this, matrices, handler.isBurning(), handler.getBurnTimeScaled(100), 100, this.left() + 64, this.top() + 42, mouseX, mouseY);

    }

    @Override
    protected void init() {
        super.init();
        this.titleY = 7;

    }
}
