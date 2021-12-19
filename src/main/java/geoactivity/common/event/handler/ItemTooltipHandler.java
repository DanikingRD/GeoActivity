package geoactivity.common.event.handler;

import geoactivity.api.item.Rechargeable;
import geoactivity.common.util.RechargeableHelper;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class ItemTooltipHandler implements ItemTooltipCallback {

    @Override
    public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if (stack.getItem() instanceof Rechargeable) {
            RechargeableHelper.loadTooltips(stack, lines);
        }
    }
}
