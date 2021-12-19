package geoactivity.client.integration.rei.category;

import geoactivity.client.integration.rei.GAREIPlugin;
import geoactivity.client.integration.rei.display.ConversionDisplay;
import geoactivity.common.registry.GAObjects;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ConversionCategory implements DisplayCategory<ConversionDisplay> {

    public static final Text TITLE = new TranslatableText("rei.geoactivity.conversion");
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(GAObjects.AUTO_STONE_BUILDER);

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public Text getTitle() {
        return TITLE;
    }

    @Override
    public int getDisplayHeight() {
        return 60;
    }

    @Override
    public CategoryIdentifier<? extends ConversionDisplay> getCategoryIdentifier() {
        return GAREIPlugin.CONVERSION;
    }

    @Override
    public List<Widget> setupDisplay(ConversionDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 64, bounds.getCenterY() - 16);
        final Point outputPoint = new Point(startPoint.getX() + 84, startPoint.getY() + 11);
        final List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        final Text key = new TranslatableText(display.getBuilderItem().getTranslationKey());
        final Text tooltip = new TranslatableText("rei.geoactivity.tooltip.builder", new TranslatableText(key.getString()));
        widgets.add(Widgets.createLabel(new Point(startPoint.getX() + 66, startPoint.getY() - 10), key)
                .noShadow()
                .tooltipLine(tooltip.getString())
                .centered().color(0xFF404040, 0xFFBBBBBB));
        widgets.add(Widgets.createArrow(new Point(startPoint.getX() + 50, startPoint.getY() + 10)).disableAnimation());
        widgets.add(Widgets.createSlot(new Point(startPoint.getX() + 27, startPoint.getY() + 11)).entry(display.getInputEntries().get(0).get(0)).markInput());
        widgets.add(Widgets.createResultSlotBackground(outputPoint));
        widgets.add(Widgets.createSlot(outputPoint).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());

        return widgets;
    }

    public static void init(CategoryRegistry registry) {
        registry.add(new ConversionCategory());
        registry.removePlusButton(GAREIPlugin.CONVERSION);
        registry.addWorkstations(GAREIPlugin.CONVERSION, ICON);
    }
}
