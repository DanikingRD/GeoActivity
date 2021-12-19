package geoactivity.common.recipe.crafting;

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

public record CraftingMachineSmeltingRecipe(Identifier id, Ingredient input, Ingredient perk, ItemStack output, int time) implements Recipe<Inventory> {
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
        return this.id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        final DefaultedList<Ingredient> ingredients = DefaultedList.of();
        ingredients.add(this.input);
        return ingredients;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return GARecipeTypes.CRAFTING_MACHINE_SMELTING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return GARecipeTypes.CRAFTING_MACHINE_SMELTING_RECIPE_TYPE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<CraftingMachineSmeltingRecipe> {

        @Override
        public CraftingMachineSmeltingRecipe read(Identifier id, JsonObject json) {
            return new CraftingMachineSmeltingRecipe(id, Ingredient.fromJson(JsonHelper.getObject(json, "input")), Ingredient.fromJson(JsonHelper.getObject(json, "perk")), ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")), JsonHelper.getInt(json, "time"));
        }

        @Override
        public CraftingMachineSmeltingRecipe read(Identifier id, PacketByteBuf buf) {
            return new CraftingMachineSmeltingRecipe(id, Ingredient.fromPacket(buf), Ingredient.fromPacket(buf), buf.readItemStack(), buf.readInt());
        }

        @Override
        public void write(PacketByteBuf buf, CraftingMachineSmeltingRecipe recipe) {
            recipe.input.write(buf);
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output());
            buf.writeInt(recipe.time());
        }
    }

}
