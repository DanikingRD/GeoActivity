package geoactivity.common.recipe;

import com.google.gson.JsonObject;
import geoactivity.common.registry.GARecipeTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public record ConversionRecipe(Identifier id, Item builder, Item input, ItemStack output) implements Recipe<Inventory> {

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.output;
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
    public RecipeSerializer<?> getSerializer() {
        return GARecipeTypes.CONVERSION_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return GARecipeTypes.CONVERSION_RECIPE_TYPE;
    }

    public Item getInput() {
        return input;
    }

    public Item getOwner() {
        return builder;
    }

    public static class Serializer implements RecipeSerializer<ConversionRecipe> {

        @Override
        public ConversionRecipe read(Identifier id, JsonObject json) {
            return new ConversionRecipe(id, Registry.ITEM.get(new Identifier(JsonHelper.getString(json, "builder"))), Registry.ITEM.get(new Identifier(JsonHelper.getString(json, "input"))), ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")));
        }

        @Override
        public ConversionRecipe read(Identifier id, PacketByteBuf buf) {
            return new ConversionRecipe(id, Registry.ITEM.get(new Identifier(buf.readString())), Registry.ITEM.get(new Identifier(buf.readString())), buf.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buf, ConversionRecipe recipe) {
            buf.writeString(Registry.ITEM.getId(recipe.getOwner()).toString());
            buf.writeString(Registry.ITEM.getId(recipe.getInput()).toString());
            buf.writeItemStack(recipe.getOutput());
        }
    }
}