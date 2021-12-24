package daniking.geoactivity.client.integration.rei;


import daniking.geoactivity.GeoActivity;
import daniking.geoactivity.client.gui.screen.AutoStoneBuilderScreen;
import daniking.geoactivity.client.gui.screen.CoalRefinerScreen;
import daniking.geoactivity.client.gui.screen.CraftingMachineScreen;
import daniking.geoactivity.client.integration.rei.category.ConversionCategory;
import daniking.geoactivity.client.integration.rei.category.RefinementCategory;
import daniking.geoactivity.client.integration.rei.category.crafting.CraftingMachineCategory;
import daniking.geoactivity.client.integration.rei.display.ConversionDisplay;
import daniking.geoactivity.client.integration.rei.display.RefinementDisplay;
import daniking.geoactivity.client.integration.rei.display.crafting.CraftingMachineShapedDisplay;
import daniking.geoactivity.client.integration.rei.display.crafting.CraftingMachineShapelessDisplay;
import daniking.geoactivity.common.recipe.ConversionRecipe;
import daniking.geoactivity.common.recipe.RefinementRecipe;
import daniking.geoactivity.common.recipe.crafting.CraftingMachineShapedRecipe;
import daniking.geoactivity.common.recipe.crafting.CraftingMachineShapelessRecipe;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GAREIPlugin implements REIClientPlugin {

    public static final CategoryIdentifier<RefinementDisplay> REFINEMENT = CategoryIdentifier.of(new Identifier(GeoActivity.MODID, "refinement"));
    public static final CategoryIdentifier<ConversionDisplay> CONVERSION = CategoryIdentifier.of(new Identifier(GeoActivity.MODID, "conversion"));
    public static final CategoryIdentifier<SimpleGridMenuDisplay> CRAFTING_MACHINE = CategoryIdentifier.of(new Identifier(GeoActivity.MODID, "crafting_machine"));

    @Override
    public void registerCategories(CategoryRegistry registry) {
        RefinementCategory.init(registry);
        ConversionCategory.init(registry);
        CraftingMachineCategory.init(registry);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(RefinementRecipe.class, RefinementDisplay::new);
        registry.registerFiller(ConversionRecipe.class, ConversionDisplay::new);
        registry.registerFiller(CraftingMachineShapedRecipe.class, CraftingMachineShapedDisplay::new);
        registry.registerFiller(CraftingMachineShapelessRecipe.class, CraftingMachineShapelessDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(129, 61, 13, 13), CoalRefinerScreen.class, REFINEMENT);
        registry.registerContainerClickArea(new Rectangle(125, 60, 13, 13), CraftingMachineScreen.class, CRAFTING_MACHINE);
        registry.registerContainerClickArea(new Rectangle(77, 48, 22, 16), AutoStoneBuilderScreen.class, CONVERSION);
    }
}
