package daniking.geoactivity.common.util;

import daniking.geoactivity.api.item.Rechargeable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class RechargeUtil {

    public static final String FATIGUED_KEY = Rechargeable.FATIGUED_NBT_KEY;


//    public static void loadTooltips(final ItemStack stack, final List<Text> tooltipList) {
//        int i = NbtHelper.getKeys(stack).size();
//        if (i == 1) {
//            return;
//        }
//        int index = 1;
//        if (i >= 2) {
//            if (!Screen.hasShiftDown()) {
//                tooltipList.add(index, new TranslatableText("geoactivity.tooltip.more_info", new TranslatableText("geoactivity.tooltip.shift").formatted(Formatting.GREEN)).formatted(Formatting.GRAY));
//                return;
//            }
//        }
//        if (isDestroyed(stack)) {
//            tooltipList.add(index, new TranslatableText("geoactivity.tooltip.fatigued").formatted(Formatting.RED));
//            index += 1;
//        }
//        if (stack.getItem() instanceof BuilderItem) {
//            GAInventory inventory = GAInventory.create(stack, 3);
//            int count = inventory.getStack(2).isEmpty() ? 0 : inventory.getStack(1).getCount();
//            tooltipList.add(index, new TranslatableText("geoactivity.tooltip.blocks", count).formatted(Formatting.GOLD));
//        }
//    }

    public static void initDestroyedNbt(PlayerEntity player, ItemStack stack) {
        if (!player.isCreative()) {
            if (isAlmostBroken(stack)) {
                markFatigued(stack);
            }
        }
    }

    public static void markFatigued(final ItemStack stack) {
        NbtHelper.putBool(stack, FATIGUED_KEY, true);
    }

    public static boolean isFatigued(final ItemStack stack) {
        return NbtHelper.getBool(stack, FATIGUED_KEY);
    }

    private static boolean isAlmostBroken(final ItemStack stack) {
        return stack.getDamage() >= stack.getMaxDamage() - 2;
    }
}
