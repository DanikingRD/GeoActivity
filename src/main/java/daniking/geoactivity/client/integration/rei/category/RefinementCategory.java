package daniking.geoactivity.client.integration.rei.category;

import daniking.geoactivity.client.integration.rei.GAREIPlugin;
import daniking.geoactivity.client.integration.rei.display.RefinementDisplay;
import daniking.geoactivity.common.registry.GAObjects;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class RefinementCategory implements DisplayCategory<RefinementDisplay> {

    public static final Text TITLE = new TranslatableText("rei.geoactivity.refinement");
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(GAObjects.COAL_REFINER);

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
        return 55;
    }

    @Override
    public CategoryIdentifier<? extends RefinementDisplay> getCategoryIdentifier() {
        return GAREIPlugin.REFINEMENT;
    }

    @Override
    public List<Widget> setupDisplay(RefinementDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 16);
        final double time = display.getTime();
        final DecimalFormat df = new DecimalFormat("###.##");
        final List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 9)));
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 1, startPoint.y + 20)).animationDurationMS(10000));
        widgets.add(Widgets.createLabel(new Point(bounds.getX() + bounds.getWidth() - 5, bounds.getY() + 5), new TranslatableText("category.rei.cooking.time&xp", df.format(display.getExperience()), df.format(time / 20d))).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y + 8)).animationDurationTicks(time));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 9)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 9)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1)).entries(display.getInputEntries().get(0)).markInput());
        return widgets;
    }

    public static void init(CategoryRegistry registry) {
        registry.add(new RefinementCategory());
        registry.addWorkstations(GAREIPlugin.REFINEMENT, ICON);
        registry.removePlusButton(GAREIPlugin.REFINEMENT);
    }
}
