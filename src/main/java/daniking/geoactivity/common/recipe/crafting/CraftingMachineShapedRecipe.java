package daniking.geoactivity.common.recipe.crafting;

import com.google.gson.JsonObject;
import daniking.geoactivity.common.registry.GARecipeTypes;
import daniking.geoactivity.common.util.RecipeUtil;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Map;

public record CraftingMachineShapedRecipe(Identifier id, int width, int height, DefaultedList<Ingredient> input, ItemStack output) implements IMachineCraftingRecipe {

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        return this.getOutput().copy();
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
    public RecipeSerializer<?> getSerializer() {
        return GARecipeTypes.CRAFTING_MACHINE_SHAPED_RECIPE_SERIALIZER;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        for (int i = 0; i <= craftingInventory.getWidth() - this.width; ++i) {
            for (int j = 0; j <= craftingInventory.getHeight() - this.height; ++j) {
                if (this.matchesPattern(craftingInventory, i, j, true)) {
                    return true;
                }

                if (this.matchesPattern(craftingInventory, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchesPattern(CraftingInventory inv, int offsetX, int offsetY, boolean flipped) {
        for (int i = 0; i < inv.getWidth(); ++i) {
            for (int j = 0; j < inv.getHeight(); ++j) {
                int k = i - offsetX;
                int l = j - offsetY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (flipped) {
                        ingredient = this.input.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = this.input.get(k + l * this.width);
                    }
                }
                if (!ingredient.test(inv.getStack(i + j * inv.getWidth()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class Serializer implements RecipeSerializer<CraftingMachineShapedRecipe> {

        public CraftingMachineShapedRecipe read(Identifier id, JsonObject json) {
            Map<String, Ingredient> map = RecipeUtil.readJsonSymbols((JsonHelper.getObject(json, "key")));
            final String[] strings = RecipeUtil.getCleanedPattern(JsonHelper.getArray(json, "pattern"));
            int i = strings[0].length();
            int j = strings.length;
            return new CraftingMachineShapedRecipe(id, i, j, RecipeUtil.setPatternMatrix(strings, map, i, j), RecipeUtil.deserializeItemFromJson(JsonHelper.getObject(json, "result")));
        }

        public CraftingMachineShapedRecipe read(Identifier id, PacketByteBuf packetByteBuf) {
            final int i = packetByteBuf.readVarInt();
            final int j = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> listOfIngredients = DefaultedList.ofSize(i * j, Ingredient.EMPTY);
            for (int k = 0; k < listOfIngredients.size(); ++k) {
                listOfIngredients.set(k, Ingredient.fromPacket(packetByteBuf));
            }
            return new CraftingMachineShapedRecipe(id, i, j, listOfIngredients, packetByteBuf.readItemStack());
        }

        public void write(PacketByteBuf packetByteBuf, CraftingMachineShapedRecipe recipe) {
            packetByteBuf.writeVarInt(recipe.width());
            packetByteBuf.writeVarInt(recipe.height());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeItemStack(recipe.getOutput());
        }
    }
}
