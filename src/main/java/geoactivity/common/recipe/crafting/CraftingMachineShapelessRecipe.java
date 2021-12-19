package geoactivity.common.recipe.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import geoactivity.common.registry.GARecipeTypes;
import geoactivity.common.util.RecipeUtil;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record CraftingMachineShapelessRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> input) implements IMachineCrafting {

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        final RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for(int j = 0; j < craftingInventory.size(); ++j) {
            ItemStack itemStack = craftingInventory.getStack(j);
            if (!itemStack.isEmpty()) {
                ++i;
                recipeMatcher.addInput(itemStack, 1);
            }
        }
        return i == this.input.size() && recipeMatcher.match(this, null);
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        return this.output.copy();
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GARecipeTypes.CRAFTING_MACHINE_SHAPELESS_RECIPE_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<CraftingMachineShapelessRecipe> {
        public CraftingMachineShapelessRecipe read(Identifier id, JsonObject json) {
            final JsonArray array = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> ingredientsFromJson = DefaultedList.of();
            for (int i = 0; i < array.size(); i++) {
                final Ingredient jsonIngredient = Ingredient.fromJson(array.get(i));
                if (!jsonIngredient.isEmpty()) {
                    ingredientsFromJson.add(jsonIngredient);
                }
            }
            if (ingredientsFromJson.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (ingredientsFromJson.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            } else {
                return new CraftingMachineShapelessRecipe(id, RecipeUtil.deserializeItemFromJson(JsonHelper.getObject(json, "result")), ingredientsFromJson);
            }
        }
        public CraftingMachineShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            final DefaultedList<Ingredient> listOfJsonIngredients = DefaultedList.ofSize(packetByteBuf.readVarInt(), Ingredient.EMPTY);
            for(int i = 0; i < listOfJsonIngredients.size(); ++i) {
                listOfJsonIngredients.set(i, Ingredient.fromPacket(packetByteBuf));
            }
            return new CraftingMachineShapelessRecipe(identifier, packetByteBuf.readItemStack(), listOfJsonIngredients);
        }

        public void write(PacketByteBuf packetByteBuf, CraftingMachineShapelessRecipe CraftingMachineShapelessRecipe) {
            packetByteBuf.writeVarInt(CraftingMachineShapelessRecipe.input.size());
            for (Ingredient ingredient : CraftingMachineShapelessRecipe.input) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeItemStack(CraftingMachineShapelessRecipe.output);
        }
    }
}
