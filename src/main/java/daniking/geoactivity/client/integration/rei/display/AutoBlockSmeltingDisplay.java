package daniking.geoactivity.client.integration.rei.display;

import daniking.geoactivity.client.integration.rei.GAREIPlugin;
import daniking.geoactivity.common.recipe.AutoBlockSmeltingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class AutoBlockSmeltingDisplay implements Display {

    private final List<EntryIngredient> input, output;

    public AutoBlockSmeltingDisplay(final AutoBlockSmeltingRecipe recipe) {
        this.input = Collections.singletonList(EntryIngredients.of(new ItemStack(recipe.input())));
        this.output = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return this.output;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GAREIPlugin.AUTO_BLOCK_SMELTING;
    }

}
