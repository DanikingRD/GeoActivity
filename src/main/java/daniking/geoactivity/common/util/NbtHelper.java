package daniking.geoactivity.common.util;

import net.minecraft.item.ItemStack;

import java.util.Set;

public class NbtHelper {

    public static void putBool(final ItemStack stack, final String key, final boolean bool) {
        stack.getOrCreateNbt().putBoolean(key, bool);
    }

    public static void remove(final ItemStack stack, final String key) {
        stack.getOrCreateNbt().remove(key);
    }

    public static boolean getBool(final ItemStack stack, final String key) {
        return checkNbt(stack, key) && stack.getOrCreateNbt().getBoolean(key);
    }

    public static Set<String> getKeys(final ItemStack stack) {
        return stack.getOrCreateNbt().getKeys();
    }

    public static boolean checkNbt(final ItemStack stack, final String key) {
        return !stack.isEmpty() && stack.getOrCreateNbt().contains(key);
    }

}
