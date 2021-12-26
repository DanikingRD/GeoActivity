package daniking.geoactivity.client.event.handler;

import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.util.TooltipUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ItemTooltipHandler implements ItemTooltipCallback {

    @Override
    public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        // Can currently be executed by a ForkJoinPool.commonPool-worker when REI is in async search mode
        // We skip this method until a thread-safe solution is in place
        if (!MinecraftClient.getInstance().isOnThread()) {
            return;
        }
        Item item = stack.getItem();
        if (item instanceof Rechargeable) {
            TooltipUtil.loadItemTooltips(stack, lines);
        }
    }
}
