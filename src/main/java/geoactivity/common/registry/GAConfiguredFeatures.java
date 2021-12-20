package geoactivity.common.registry;

import geoactivity.GeoActivity;
import geoactivity.mixin.OrePlacedFeaturesAccessor;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public final class GAConfiguredFeatures {
    
    //targets
    public static final List<OreFeatureConfig.Target> LIGNITE_ORES = List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, GAObjects.LIGNITE_ORE.getDefaultState()), OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GAObjects.DEEPSLATE_LIGNITE_ORE.getDefaultState()));
    public static final List<OreFeatureConfig.Target> BITUMINOUS_ORES = List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, GAObjects.BITUMINOUS_ORE.getDefaultState()), OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GAObjects.DEEPSLATE_BITUMINOUS_ORE.getDefaultState()));
    public static final List<OreFeatureConfig.Target> ANTHRACITE_ORES = List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, GAObjects.ANTHRACITE_ORE.getDefaultState()), OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GAObjects.DEEPSLATE_ANTHRACITE_ORE.getDefaultState()));
    //ore features
    public static final ConfiguredFeature<?, ?> LIGNITE_ORE_FEATURE = Feature.ORE.configure(new OreFeatureConfig(LIGNITE_ORES, 8));
    public static final ConfiguredFeature<?, ?> LIGNITE_ORE_BURIED_FEATURE = Feature.ORE.configure(new OreFeatureConfig(LIGNITE_ORES, 8, 0.5F));
    public static final ConfiguredFeature<?, ?> BITUMINOUS_ORE_FEATURE = Feature.ORE.configure(new OreFeatureConfig(BITUMINOUS_ORES, 7));
    public static final ConfiguredFeature<?, ?> BITUMINOUS_ORE_BURIED_FEATURE = Feature.ORE.configure(new OreFeatureConfig(BITUMINOUS_ORES, 7, 0.5F));
    public static final ConfiguredFeature<?, ?> ANTHRACITE_ORE_FEATURE = Feature.ORE.configure(new OreFeatureConfig(ANTHRACITE_ORES, 4, 0.5F));
    public static final ConfiguredFeature<?,?>  ANTHRACITE_ORE_LARGE_FEATURE = Feature.ORE.configure(new OreFeatureConfig(ANTHRACITE_ORES, 9, 0.7F));
    public static final ConfiguredFeature<?, ?> ANTHRACITE_ORE_BURIED_FEATURE = Feature.ORE.configure(new OreFeatureConfig(ANTHRACITE_ORES, 6, 1.0F));
    //follows coal
    public static final PlacedFeature LIGNITE_ORE_UPPER = LIGNITE_ORE_FEATURE.withPlacement(OrePlacedFeaturesAccessor.callModifiersWithCount(30, HeightRangePlacementModifier.uniform(YOffset.fixed(136), YOffset.getTop())));
    public static final PlacedFeature LIGNITE_ORE_LOWER = LIGNITE_ORE_BURIED_FEATURE.withPlacement(OrePlacedFeaturesAccessor.callModifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(192))));
    //follows lapis
    public static final PlacedFeature BITUMINOUS_ORE_UPPER = BITUMINOUS_ORE_FEATURE.withPlacement(OrePlacedFeaturesAccessor.callModifiersWithCount(2, HeightRangePlacementModifier.uniform(YOffset.fixed(-32), YOffset.fixed(32))));
    public static final PlacedFeature BITUMINOUS_ORE_LOWER = BITUMINOUS_ORE_BURIED_FEATURE.withPlacement(OrePlacedFeaturesAccessor.callModifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.getBottom(), YOffset.fixed(64))));
    //follows diamond
    public static final PlacedFeature ANTHRACITE_ORE_UPPER = ANTHRACITE_ORE_FEATURE.withPlacement(OrePlacedFeaturesAccessor.callModifiersWithCount(7,HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
    public static final PlacedFeature ANTHRACITE_ORE_LARGE = ANTHRACITE_ORE_LARGE_FEATURE.withPlacement(OrePlacedFeaturesAccessor.callModifiersWithRarity(9, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
    public static final PlacedFeature ANTHRACITE_ORE_LOWER = ANTHRACITE_ORE_BURIED_FEATURE.withPlacement(OrePlacedFeaturesAccessor.callModifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));

    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(GeoActivity.MODID, "lignite_ore"), LIGNITE_ORE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(GeoActivity.MODID, "lignite_ore_buried"), LIGNITE_ORE_BURIED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(GeoActivity.MODID, "lignite_ore_upper"), LIGNITE_ORE_UPPER);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(GeoActivity.MODID, "lignite_ore_lower"), LIGNITE_ORE_LOWER);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(GeoActivity.MODID, "bituminous_ore"), BITUMINOUS_ORE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(GeoActivity.MODID, "bituminous_ore_buried"), BITUMINOUS_ORE_BURIED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(GeoActivity.MODID, "bituminous_ore_upper"), BITUMINOUS_ORE_UPPER);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(GeoActivity.MODID, "bituminous_ore_lower"), BITUMINOUS_ORE_LOWER);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(GeoActivity.MODID, "anthracite_ore"), ANTHRACITE_ORE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(GeoActivity.MODID, "anthracite_ore_large"), ANTHRACITE_ORE_LARGE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(GeoActivity.MODID, "anthracite_ore_buried"), ANTHRACITE_ORE_BURIED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(GeoActivity.MODID, "anthracite_ore_upper"), ANTHRACITE_ORE_UPPER);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(GeoActivity.MODID, "anthracite_ore_large"), ANTHRACITE_ORE_LARGE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(GeoActivity.MODID, "anthracite_ore_lower"), ANTHRACITE_ORE_LOWER);
        final BiomeModification worldGeneration = BiomeModifications.create(new Identifier(GeoActivity.MODID, "world_generation"));
        worldGeneration.add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), ctx -> {
            if (GeoActivity.config.generateLignite) {
                ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, LIGNITE_ORE_UPPER);
                ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, LIGNITE_ORE_LOWER);
            }
            if (GeoActivity.config.generateBituminous) {
                ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, BITUMINOUS_ORE_UPPER);
                ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, BITUMINOUS_ORE_LOWER);
            }
            if (GeoActivity.config.generateAnthracite) {
                ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, ANTHRACITE_ORE_UPPER);
                ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, ANTHRACITE_ORE_LARGE);
                ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, ANTHRACITE_ORE_LOWER);
            }
        });
    }

}
