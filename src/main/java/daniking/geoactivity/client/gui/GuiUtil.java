package daniking.geoactivity.client.gui;

import daniking.geoactivity.api.gui.GuiBase;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class GuiUtil {

    public static void drawPercentageTooltip(GuiBase<?> base, MatrixStack stack, int x, int y, int width, int height, int xMouse, int yMouse, int value, int max) {
        x -= base.left();
        y -= base.top();
        if (base.isMouseAt(x, y, width, height, xMouse, yMouse)) {
            int percentage = percentageOf(value, max);
            base.renderTooltip(stack, new LiteralText(String.valueOf(percentage))
                            .formatted(percentageColor(percentage))
                            .append("%"), xMouse, yMouse);
        }
    }

    public static int percentageOf(long value, long max) {
        if (value == 0) {
            return 0;
        } else {
            return (int) ((value * 100.0f) / max);
        }
    }

    public static Formatting percentageColor(int percentage) {
        if (percentage <= 10) {
            return Formatting.RED;
        } else if (percentage >= 75) {
            return Formatting.GREEN;
        } else {
            return Formatting.YELLOW;
        }
    }

}
