package daniking.geoactivity.client.integration.rei.display.crafting;

import daniking.geoactivity.client.integration.rei.GAREIPlugin;
import daniking.geoactivity.common.recipe.crafting.CraftingMachineShapelessRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CraftingMachineShapelessDisplay implements Display {

    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> outputs;

    public CraftingMachineShapelessDisplay(CraftingMachineShapelessRecipe recipe) {
        this.inputs = EntryIngredients.ofIngredients(recipe.getIngredients());
        this.outputs = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
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
}
