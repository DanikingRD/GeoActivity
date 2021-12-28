package daniking.geoactivity.client.integration.patchouli;

import daniking.geoactivity.common.recipe.crafting.CraftingMachineShapedRecipe;
import daniking.geoactivity.common.recipe.crafting.IMachineCraftingRecipe;
import daniking.geoactivity.common.registry.GARecipeTypes;
import net.minecraft.client.MinecraftClient;
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
        if (key.startsWith("item")) {
            int index = Integer.parseInt(key.substring("item".length()));
            if (this.recipe instanceof CraftingMachineShapedRecipe shaped) {
                //fix small precious alloy ingot recipe
                if (shaped.width() == 1) {
                    if (index == 1 || index == 4 || index == 7) {
                        index = (index-1)/3 + 1; //shifts [1, 4, 7] to [1, 2, 3]
                    } else {
                        return IVariable.empty();
                    }
                }
            }
            Ingredient ingredient;
            try {
                ingredient = this.recipe.getIngredients().get(index-1);
            } catch (Exception e) {
                ingredient = Ingredient.empty();
            }

            return IVariable.from(ingredient.getMatchingStacks());
        } else if (key.equals("header")) {
            return IVariable.from(this.recipe.getOutput().getName());
        } else if (key.equals("output")) {
            return IVariable.from(recipe.getOutput());
        } else {
            return IVariable.empty();
        }
    }
}
