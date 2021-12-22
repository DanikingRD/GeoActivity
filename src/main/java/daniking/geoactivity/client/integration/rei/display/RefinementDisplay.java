package daniking.geoactivity.client.integration.rei.display;

import daniking.geoactivity.client.integration.rei.GAREIPlugin;
import daniking.geoactivity.common.recipe.RefinementRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class RefinementDisplay implements Display {

    private final List<EntryIngredient> input, output;
    private final int time;
    private final float experience;

    public RefinementDisplay(RefinementRecipe recipe) {
        this.input = EntryIngredients.ofIngredients(recipe.getIngredients());
        this.output = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
        this.time = recipe.time();
        this.experience = recipe.experience();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return this.output;
    }

    public float getExperience() {
        return experience;
    }

    public int getTime() {
        return time;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GAREIPlugin.REFINEMENT;
    }
}
