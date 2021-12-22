package daniking.geoactivity.common.recipe.crafting;

import daniking.geoactivity.common.registry.GARecipeTypes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public interface IMachineCrafting extends Recipe<CraftingInventory> {

    @Override
    default RecipeType<?> getType() {
        return GARecipeTypes.MACHINE_CRAFTING_RECIPE;
    }

    @Override
    default boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    default boolean fits(int width, int height) {
        return false;
    }
}
