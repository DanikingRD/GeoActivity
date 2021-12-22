package daniking.geoactivity.client.integration.patchouli;

import daniking.geoactivity.common.recipe.RefinementRecipe;
import daniking.geoactivity.common.registry.GARecipeTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class RefinementProcessor implements IComponentProcessor {

    private RefinementRecipe recipe;

    @Override
    public void setup(IVariableProvider variables) {
        final String recipeId = variables.get("recipe").asString();
        final RecipeManager manager = MinecraftClient.getInstance().world.getRecipeManager();
        this.recipe = (RefinementRecipe) manager.get(new Identifier(recipeId)).filter(recipe -> recipe.getType().equals(GARecipeTypes.REFINEMENT_RECIPE_TYPE)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public IVariable process(String key) {
        switch (key) {
            case "output":
                return IVariable.from(recipe.getOutput());
            case "header":
                return IVariable.from(recipe.getOutput().getName());
            case "ingredient0":
                ItemStack[] stacks = recipe.input().getMatchingStacks();
                return stacks.length > 0 ? IVariable.from(stacks[0]) : null;
        }
        return null;
    }
}
