package daniking.geoactivity.api.gui.builder;

import com.mojang.blaze3d.systems.RenderSystem;
import daniking.geoactivity.GeoActivity;
import daniking.geoactivity.api.gui.GuiBase;
import daniking.geoactivity.client.gui.GuiUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public final class ScreenBuilder {

    private final Screen parent;
    private static final Identifier GUI_ELEMENTS = new Identifier(GeoActivity.MODID, "textures/gui/elements.png");

    public ScreenBuilder(final Screen parent) {
        this.parent = parent;
    }

    public void drawContainer(final MatrixStack stack, final int left, final int top, final int width, final int height) {
        this.bindTexture();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.parent.drawTexture(stack, left, top, 0, 0, width / 2, height / 2);
        this.parent.drawTexture(stack, left + width / 2, top, 150 - width / 2, 0, width / 2, height / 2);
        this.parent.drawTexture(stack, left, top + height / 2, 0, 150 - height / 2, width / 2, height / 2);
        this.parent.drawTexture(stack, left + width / 2, top + height / 2, 150 - width / 2, 150 - height / 2, width / 2, height / 2);
    }

    public void drawREIButton(MatrixStack matrixStack, final int posX,final int posY) {
        if (FabricLoader.getInstance().isModLoaded("roughlyenoughitems")) {
            this.bindTexture();
            this.parent.drawTexture(matrixStack, posX, posY, 0, 150, 13, 13);
        }
    }

    public void drawContainer3x3(final MatrixStack matrixStack, final int posX, final int posY, SlotType type) {
        this.bindTexture();
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                switch (type) {
                    case DEFAULT -> this.parent.drawTexture(matrixStack, posX + y * 18, posY + x * 18, 238, 0, 18, 18);
                    case DIAMOND ->  this.parent.drawTexture(matrixStack, posX + y * 18, posY + x * 18, 220, 0, 18, 18);
                }
            }
        }
    }

    public void drawPlayerSlots(final MatrixStack matrixStack, final int posX, final int posY) {
        this.bindTexture();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 9; y++) {
                this.parent.drawTexture(matrixStack, posX + y * 18, posY + x * 18, 238, 0, 18, 18);
            }
        }
        final int offsetY = 58;
        for (int x = 0; x < 9; x++) {
            this.parent.drawTexture(matrixStack, posX + x * 18, posY + offsetY, 238, 0, 18, 18);
        }
    }

    public void drawSlot(final MatrixStack matrixStack, final int posX, final int posY, SlotType type) {
        this.bindTexture();
        switch (type) {
            case DEFAULT -> this.parent.drawTexture(matrixStack, posX, posY, 238, 0, 18, 18);
            case DIAMOND -> this.parent.drawTexture(matrixStack, posX, posY, 220, 0, 18, 18);
        }
    }

    public void drawOutputSlot(final MatrixStack matrixStack, final int posX, final int posY, SlotType type) {
        this.bindTexture();
        switch (type) {
            case DEFAULT ->  this.parent.drawTexture(matrixStack, posX, posY, 150, 0, 26,26);
            case DIAMOND -> this.parent.drawTexture(matrixStack, posX, posY, 176, 0, 26,26);
        }
    }

    //advanced coal refiner smelting progress
    public void drawDoubleSmeltingProgress(GuiBase<?> base, MatrixStack matrixStack, int posX, int posY, int progress, int maxProgress, int mouseX, int mouseY) {
        this.bindTexture();
        this.parent.drawTexture(matrixStack, posX - 2, posY - 1, 151, 43, 2, 17);
        this.parent.drawTexture(matrixStack, posX, posY, 150, 26, 22, 15);
        int i = (int) ((double) progress / (double) maxProgress * 22);
        if (i < 0) {
            i = 0;
        }
        GuiUtil.drawPercentageTooltip(base, matrixStack, posX - 2, posY, 24, 15, mouseX, mouseY, progress, maxProgress);
    }
    public void drawSmeltingProgress(GuiBase<?> base, MatrixStack matrixStack, int posX, int posY, int progress, int maxProgress, int mouseX, int mouseY) {
        this.bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, 150, 26, 22, 15);
        int i = (int) ((double) progress / (double) maxProgress * 22);
        if (i < 0) {
            i = 0;
        }
        this.parent.drawTexture(matrixStack, posX, posY, 172, 26, i, 16);
        GuiUtil.drawPercentageTooltip(base, matrixStack, posX, posY, 22, 15, mouseX, mouseY, progress, maxProgress);
    }

    public void drawBurningProgress(final GuiBase<?> base, final MatrixStack matrixStack, final boolean burning,  final int progress, final int maxProgress, final int posX, final int posY, final int mouseX, final int mouseY) {
        this.bindTexture();
        this.parent.drawTexture(matrixStack, posX, posY, 240, 34, 13, 13);

        int i = 12 - (int) ((double) progress / (double) maxProgress * 13);
        if (burning) {
            this.parent.drawTexture(matrixStack, posX,  posY + i, 240, 19 + i,  14, 14 - i);
        }

        GuiUtil.drawPercentageTooltip(base, matrixStack, posX, posY, 14, 14, mouseX, mouseY, progress, maxProgress);
    }

    public void drawCoalSprite(final MatrixStack stack, final int posX, final int posY) {
        this.bindTexture();
        this.parent.drawTexture(stack, posX, posY, 1, 164, 13, 13);
    }

    private void bindTexture() {
        this.bindTexture(GUI_ELEMENTS);
    }

    public void bindTexture(final Identifier texture) {
        RenderSystem.setShaderTexture(0, texture);
    }

    public enum SlotType {
        DEFAULT,
        DIAMOND
    }

}
