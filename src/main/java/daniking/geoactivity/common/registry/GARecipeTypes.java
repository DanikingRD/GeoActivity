package daniking.geoactivity.common.registry;

import daniking.geoactivity.GeoActivity;
import daniking.geoactivity.common.recipe.AutoBlockSmeltingRecipe;
import daniking.geoactivity.common.recipe.RefinementRecipe;
import daniking.geoactivity.common.recipe.crafting.CraftingMachineShapedRecipe;
import daniking.geoactivity.common.recipe.crafting.CraftingMachineShapelessRecipe;
import daniking.geoactivity.common.recipe.crafting.IMachineCraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public final class GARecipeTypes {

    private static final Map<RecipeSerializer<?>, Identifier> RECIPE_SERIALIZERS = new HashMap<>();
    private static final Map<RecipeType<?>, Identifier> RECIPE_TYPES = new HashMap<>();

    public static final RecipeSerializer<RefinementRecipe> REFINEMENT_RECIPE_SERIALIZER = register("refinement", new  RefinementRecipe.Serializer());
    public static final RecipeType<RefinementRecipe> REFINEMENT_RECIPE_TYPE = register("refinement");

    public static final RecipeSerializer<CraftingMachineShapedRecipe> CRAFTING_MACHINE_SHAPED_RECIPE_SERIALIZER = register("advanced_crafting_shaped", new CraftingMachineShapedRecipe.Serializer());
    public static final RecipeSerializer<CraftingMachineShapelessRecipe> CRAFTING_MACHINE_SHAPELESS_RECIPE_SERIALIZER = register("advanced_crafting_shapeless", new CraftingMachineShapelessRecipe.Serializer());
    public static final RecipeType<IMachineCraftingRecipe> MACHINE_CRAFTING_RECIPE_TYPE = register("advanced_crafting");

    public static final RecipeType<AutoBlockSmeltingRecipe> AUTO_BLOCK_SMELTING_RECIPE_TYPE = register("auto_block_smelting");
    public static final RecipeSerializer<AutoBlockSmeltingRecipe> AUTO_BLOCK_SMELTING_RECIPE_SERIALIZER = register("auto_block_smelting", new AutoBlockSmeltingRecipe.Serializer());

    private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        RECIPE_SERIALIZERS.put(serializer, new Identifier(GeoActivity.MODID, name));
        return serializer;
    }

    private static <T extends Recipe<?>> RecipeType<T> register(String name) {
        final RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        RECIPE_TYPES.put(type, new Identifier(GeoActivity.MODID, name));
        return type;
    }

    public static void init() {
        RECIPE_SERIALIZERS.keySet().forEach(recipeSerializer -> Registry.register(Registry.RECIPE_SERIALIZER, RECIPE_SERIALIZERS.get(recipeSerializer), recipeSerializer));
        RECIPE_TYPES.keySet().forEach(recipeType -> Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPES.get(recipeType), recipeType));
    }
}

