package daniking.geoactivity.client.integration.rei.display.crafting;

import daniking.geoactivity.client.integration.rei.GAREIPlugin;
import daniking.geoactivity.common.recipe.crafting.CraftingMachineShapedRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class CraftingMachineShapedDisplay implements SimpleGridMenuDisplay {

    private final CraftingMachineShapedRecipe recipe;
    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> outputs;

    public CraftingMachineShapedDisplay(CraftingMachineShapedRecipe recipe) {
        this.recipe = Objects.requireNonNull(recipe);
        this.inputs = EntryIngredients.ofIngredients(recipe.getIngredients());
        this.outputs = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
    }

    @Override
    public int getWidth() {
        return recipe.width();
    }

    @Override
    public int getHeight() {
        return recipe.height();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return this.outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GAREIPlugin.CRAFTING_MACHINE;
    }

    public static int getSlotWithSize(int recipeWidth, int index, int craftingGridWidth) {
        int x = index % recipeWidth;
        int y = (index - x) / recipeWidth;
        return craftingGridWidth * y + x;
    }
}
