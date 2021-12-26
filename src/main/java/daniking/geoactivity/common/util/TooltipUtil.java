package daniking.geoactivity.common.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class TooltipUtil {

    private static final Formatting INFO_COLOR = Formatting.GOLD;
    private static final Formatting NEGATIVE_COLOR = Formatting.RED;
    public static void loadItemTooltips(ItemStack stack, List<Text> list) {
        if (Screen.hasShiftDown()) {
            final String key = "geoactivity.tooltip." + Registry.ITEM.getId(stack.getItem()).getPath();
            if (I18n.hasTranslation(key)) {
                list.add(1, new TranslatableText(key).formatted(INFO_COLOR));
            }
            if (RechargeUtil.isFatigued(stack)) {
                list.add(1, new TranslatableText("geoactivity.tooltip.fatigued").formatted(NEGATIVE_COLOR));
            }
        } else {
            list.add(1, new TranslatableText("geoactivity.tooltip.more_info", new TranslatableText("geoactivity.tooltip.shift").formatted(Formatting.GREEN)).formatted(Formatting.GRAY));
        }
    }
}
