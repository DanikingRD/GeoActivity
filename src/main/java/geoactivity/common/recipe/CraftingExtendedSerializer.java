package geoactivity.common.recipe;

import com.google.gson.JsonObject;
import geoactivity.common.util.RecipeUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Map;

public class CraftingExtendedSerializer implements RecipeSerializer<ShapedRecipe> {

    public ShapedRecipe read(Identifier id, JsonObject json) {
        Map<String, Ingredient> map = RecipeUtil.readJsonSymbols((JsonHelper.getObject(json, "key")));
        final String[] strings = RecipeUtil.getCleanedPattern(JsonHelper.getArray(json, "pattern"));
        int i = strings[0].length();
        int j = strings.length;
        return new ShapedRecipe(id, "", i, j, RecipeUtil.setPatternMatrix(strings, map, i, j), RecipeUtil.deserializeItemFromJson(JsonHelper.getObject(json, "result")));
    }
    public ShapedRecipe read(Identifier id, PacketByteBuf packetByteBuf) {
        final int i = packetByteBuf.readVarInt();
        final int j = packetByteBuf.readVarInt();
        DefaultedList<Ingredient> listOfIngredients = DefaultedList.ofSize(i * j, Ingredient.EMPTY);
        for (int k = 0; k < listOfIngredients.size(); ++k) {
            listOfIngredients.set(k, Ingredient.fromPacket(packetByteBuf));
        }
        return new ShapedRecipe(id, "", i, j, listOfIngredients, packetByteBuf.readItemStack());
    }
    public void write(PacketByteBuf packetByteBuf, ShapedRecipe shapedRecipe) {
        packetByteBuf.writeVarInt(shapedRecipe.getWidth());
        packetByteBuf.writeVarInt(shapedRecipe.getHeight());
        for (Ingredient ingredient : shapedRecipe.getIngredients()) {
            ingredient.write(packetByteBuf);
        }
        packetByteBuf.writeItemStack(shapedRecipe.getOutput());
    }
}
