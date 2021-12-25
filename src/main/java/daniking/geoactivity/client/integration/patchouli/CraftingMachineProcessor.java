package daniking.geoactivity.client.integration.patchouli;

import daniking.geoactivity.common.recipe.crafting.IMachineCraftingRecipe;
import daniking.geoactivity.common.registry.GARecipeTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class CraftingMachineProcessor implements IComponentProcessor {

    private IMachineCraftingRecipe recipe;
    @Override
    public void setup(IVariableProvider variables) {
        String recipeId = variables.get("recipe").asString();
        RecipeManager manager = MinecraftClient.getInstance().world.getRecipeManager();
        recipe = (IMachineCraftingRecipe) manager.get(new Identifier(recipeId)).filter(recipe -> recipe.getType().equals(GARecipeTypes.MACHINE_CRAFTING_RECIPE_TYPE)).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public IVariable process(String key) {
        if (key.startsWith("ingredient")) {
            final int index = Integer.parseInt(key.substring(10));
            final Ingredient ingredient = recipe.getIngredients().get(index);
            final ItemStack[] stacks = ingredient.getMatchingStacks();
            final ItemStack stack = stacks.length == 0 ? ItemStack.EMPTY : stacks[0];

            return IVariable.from(stack);
        } else if (key.equals("header")) {
            return IVariable.from(this.recipe.getOutput().getName());
        } else if (key.equals("output")) {
            return IVariable.from(recipe.getOutput());
        } else {
            return null;
        }
    }
}
