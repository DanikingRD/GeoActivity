package daniking.geoactivity.api.gui;

import daniking.geoactivity.api.gui.builder.ScreenBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

import java.util.Objects;

public class GuiBase<T extends ScreenHandler> extends HandledScreen<T> {

    private final ScreenBuilder builder;

    public GuiBase(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.builder = new ScreenBuilder(this);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.builder.drawContainer(matrices, this.left(), this.top(), this.guiWidth(), this.guiHeight());
        this.builder.drawPlayerSlots(matrices, this.left() + 7, this.top() + 83);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        this.titleY = 14;
    }

    public boolean isMouseAt(int x, int y, int width, int height, double pointX, double pointY) {
        return super.isPointWithinBounds(x, y, width, height, pointX, pointY);
    }

    public int left() {
        return this.x;
    }

    public int top() {
        return this.y;
    }

    public int guiWidth() {
        return this.backgroundWidth;
    }

    public int guiHeight() {
        return this.backgroundHeight;
    }

    public MinecraftClient client() {
        Objects.requireNonNull(this.client, "Minecraft Client is null");
        return this.client;
    }

    public ScreenBuilder builder() {
        return builder;
    }
}
