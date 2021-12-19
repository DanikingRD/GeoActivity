package geoactivity.client.integration.rei.category.crafting;

import geoactivity.client.integration.rei.GAREIPlugin;
import geoactivity.client.integration.rei.display.crafting.CraftingMachineShapedDisplay;
import geoactivity.common.registry.GAObjects;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
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
public class CraftingMachineCategory implements DisplayCategory<Display> {

    public static final EntryStack<ItemStack> ICON = EntryStacks.of(GAObjects.CRAFTING_MACHINE);
    public static final Text TITLE = new TranslatableText("rei.geoactivity.crafting_machine");

    @Override
    public List<Widget> setupDisplay(Display display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 58, bounds.getCenterY() - 27);
        final List<Widget> list = new ArrayList<>();
        list.add(Widgets.createRecipeBase(bounds));
        list.add(Widgets.createArrow(new Point(startPoint.x + 60, startPoint.y + 18)));
        list.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 95, startPoint.y + 19)));
        List<EntryIngredient> input = display.getInputEntries();
        List<Slot> slots = new ArrayList<>();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                slots.add(Widgets.createSlot(new Point(startPoint.x + 1 + x * 18, startPoint.y + 1 + y * 18)).markInput());
            }
        }
        for (int i = 0; i < input.size(); ++i) {
            if (display instanceof CraftingMachineShapedDisplay shaped) {
                if (!input.get(i).isEmpty()) {
                    slots.get(CraftingMachineShapedDisplay.getSlotWithSize(shaped.getWidth(), i, 3)).entries(input.get(i));
                }
            } else if (!input.get(i).isEmpty()) {
                slots.get(i).entries(input.get(i));
            }
        }
        list.addAll(slots);
        list.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 19)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return list;
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public Text getTitle() {
        return TITLE;
    }

    @Override
    public CategoryIdentifier<? extends Display> getCategoryIdentifier() {
        return GAREIPlugin.CRAFTING_MACHINE;
    }

    public static void init(CategoryRegistry registry) {
        registry.add(new CraftingMachineCategory());
        registry.addWorkstations(GAREIPlugin.CRAFTING_MACHINE, ICON);
        registry.removePlusButton(GAREIPlugin.CRAFTING_MACHINE);
    }
}
