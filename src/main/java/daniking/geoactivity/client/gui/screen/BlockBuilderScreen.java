package daniking.geoactivity.client.gui.screen;

import daniking.geoactivity.api.gui.GuiBase;
import daniking.geoactivity.api.gui.builder.ScreenBuilder;
import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class BlockBuilderScreen<T extends ItemScreenHandler> extends GuiBase<T> {

    public BlockBuilderScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
        this.builder().drawSlot(matrices, this.left() + 79, this.top() + 22, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawSlot(matrices, this.left() + 52, this.top() + 43, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawSlot(matrices, this.left() + 106, this.top() + 43, ScreenBuilder.SlotType.DEFAULT);
        this.builder().drawCoalSprite(matrices, this.left() + 82, this.top() + 25);
        this.builder().drawREIButton(matrices, this.left() + 145, this.top() + 24);
        this.builder().drawSmeltingProgress(this, matrices, this.left() + 77, this.top() + 44, this.getProgress(), 100, mouseX, mouseY);
    }

    public int getProgress() {
        if (this.handler.getInventory().isEmpty()) {
            return 0;
        } else if (this.handler.getInventory().getStack(1).isEmpty()) {
            return 0;
        } else if (this.handler.getInventory().getStack(2).isEmpty()) {
            return 0;
        } else {

            return 100;
        }
    }

    @Override
    protected void init() {
        super.init();
        this.titleX += 3;
        this.titleY = 10;
    }
}
