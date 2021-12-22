package daniking.geoactivity.client.integration.rei.display;

import daniking.geoactivity.client.integration.rei.GAREIPlugin;
import daniking.geoactivity.common.recipe.ConversionRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ConversionDisplay implements Display {

    private final List<EntryIngredient> input, output;
    private final Item builderItem;

    public ConversionDisplay(final ConversionRecipe recipe) {
        this.input = Collections.singletonList(EntryIngredients.of(new ItemStack(recipe.input())));
        this.output = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
        this.builderItem = recipe.builder();
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
        return GAREIPlugin.CONVERSION;
    }

    public Item getBuilderItem() {
        return this.builderItem;
    }
}
