package daniking.geoactivity.common.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import daniking.geoactivity.api.item.Rechargeable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Set;

public class RecipeUtil {

    public static ItemStack deserializeItemFromJson(final JsonObject object) {
        final Identifier location = new Identifier(JsonHelper.getString(object, "item"));
        final Item item = Registry.ITEM.get(location);
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Invalid item: " + item);
        }
        final int i = JsonHelper.getInt(object, "count", 1);
        if (i < 1) {
            throw new JsonSyntaxException("Invalid count: " + i);
        }
        final ItemStack jsonStack = new ItemStack(item);
        if (item instanceof Rechargeable) {
            jsonStack.setDamage(jsonStack.getMaxDamage() - 2);
            RechargeUtil.onCraft(jsonStack);
        }
        return jsonStack;
    }

    public static Map<String, Ingredient> readJsonSymbols(JsonObject json) {
        Map<String, Ingredient> map = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> eachEntry : json.entrySet()) {
            if (eachEntry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + eachEntry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(eachEntry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            map.put(eachEntry.getKey(), Ingredient.fromJson(eachEntry.getValue()));
        }
        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static String[] getCleanedPattern(final JsonArray array) {
        return removePadding(readJsonPattern(array));
    }

    public static String[] readJsonPattern(JsonArray json) {
        String[] strings = new String[json.size()];
        if (strings.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        } else if (strings.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for (int i = 0; i < strings.length; ++i) {
               final String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
                if (string.length() > 3) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
                }
                if (i > 0 && strings[0].length() != string.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }
                strings[i] = string;
            }
            return strings;
        }
    }

    //This is awful
   private static String[] removePadding(String... pattern) {
        int i = 2147483647;
        int j = 0;
        int k = 0;
        int l = 0;
        for (int m = 0; m < pattern.length; ++m) {
            String string = pattern[m];
            i = Math.min(i, findFirstSymbol(string));
            int n = findLastSymbol(string);
            j = Math.max(j, n);
            if (n < 0) {
                if (k == m) {
                    ++k;
                }
                ++l;
            } else {
                l = 0;
            }
        }
        if (pattern.length == l) {
            return new String[0];
        } else {
            String[] strings = new String[pattern.length - l - k];
            for (int o = 0; o < strings.length; ++o) {
                strings[o] = pattern[o + k].substring(i, j + 1);
            }
            return strings;
        }
    }

   public static DefaultedList<Ingredient> setPatternMatrix(String[] pattern, Map<String, Ingredient> symbols, int width, int height) {
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(symbols.keySet());
        set.remove(" ");
        for (int i = 0; i < pattern.length; ++i) {
            for (int j = 0; j < pattern[i].length(); ++j) {
                String string = pattern[i].substring(j, j + 1);
                Ingredient ingredient = symbols.get(string);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
                }
                set.remove(string);
                defaultedList.set(j + width * i, ingredient);
            }
        }
        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return defaultedList;
        }
    }

    private static int findFirstSymbol(String line) {
        int i;
        for (i = 0; i < line.length() && line.charAt(i) == ' '; ++i) {

        }
        return i;
    }

    private static int findLastSymbol(String pattern) {
        int i;
        for (i = pattern.length() - 1; i >= 0 && pattern.charAt(i) == ' '; --i) {

        }
        return i;
    }



}
