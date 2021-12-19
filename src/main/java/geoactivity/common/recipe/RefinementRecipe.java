package geoactivity.common.recipe;

import com.google.gson.JsonObject;
import geoactivity.common.registry.GARecipeTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record RefinementRecipe(Identifier identifier, Ingredient input, ItemStack output, float experience, int time) implements Recipe<Inventory> {

    @Override
    public boolean matches(Inventory inventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GARecipeTypes.REFINEMENT_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return GARecipeTypes.REFINEMENT_RECIPE_TYPE;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        final DefaultedList<Ingredient> ingredients = DefaultedList.of();
        ingredients.add(this.input);
        return ingredients;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<RefinementRecipe> {

        @Override
        public RefinementRecipe read(Identifier id, JsonObject json) {
            return new RefinementRecipe(id, Ingredient.fromJson(JsonHelper.getObject(json, "ingredient")), ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")), JsonHelper.getFloat(json, "experience"), JsonHelper.getInt(json, "time"));
        }

        @Override
        public RefinementRecipe read(Identifier id, PacketByteBuf buf) {
            return new RefinementRecipe(id, Ingredient.fromPacket(buf), buf.readItemStack(), buf.readFloat(), buf.readInt());
        }

        @Override
        public void write(PacketByteBuf buf, RefinementRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output());
            buf.writeFloat(recipe.experience());
            buf.writeInt(recipe.time());
        }
    }
}

