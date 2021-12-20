package geoactivity.common.registry;

import geoactivity.GeoActivity;
import geoactivity.common.GAConfig;

//Deprecated api


@SuppressWarnings("deprecation")
public final class GAConfiguredFeatures {
    
    private static final GAConfig CONFIG = GeoActivity.config;

    //public static final List<OreFeatureConfig.Target> LIGNITE_ORES = List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, GAObjects.LIGNITE_ORE.getDefaultState()), OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GAObjects.DEEPSLATE_LIGNITE_ORE.getDefaultState()));

//    //Creates a new modification for the world
//    private static final BiomeModification WORLD_GENERATOR = BiomeModifications.create(new Identifier(GeoActivity.MODID, "configure_features"));
//
//    /**
//     * Configured Features
//     */
//    public static final ConfiguredFeature<?, ?> LIGNITE_ORE = create("lignite_ore", Feature.ORE.configure(new OreFeatureConfig(ImmutableList.of(OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, GAObjects.LIGNITE_ORE.getDefaultState())), CONFIG.ligniteVeinSize)).uniformRange(YOffset.fixed(CONFIG.ligniteMinHeight), YOffset.fixed(CONFIG.ligniteMaxHeight)).spreadHorizontally().repeat(CONFIG.ligniteVeinsPerChunk));
//    public static final ConfiguredFeature<?, ?> BITUMINOUS_ORE = create("bituminous_ore", Feature.ORE.configure(new OreFeatureConfig(ImmutableList.of(OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, GAObjects.BITUMINOUS_ORE.getDefaultState())), CONFIG.bituminousVeinSize)).uniformRange(YOffset.fixed(CONFIG.bituminousMinHeight), YOffset.fixed(CONFIG.bituminousMaxHeight)).spreadHorizontally().repeat(CONFIG.bituminousVeinsPerkChunk));
//    public static final ConfiguredFeature<?, ?> ANTHRACITE_ORE = create("anthracite_ore", Feature.ORE.configure(new OreFeatureConfig(ImmutableList.of(OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, GAObjects.ANTHRACITE_ORE.getDefaultState())), CONFIG.anthraciteVeinSize)).uniformRange(YOffset.fixed(CONFIG.anthraciteMinHeight), YOffset.fixed(CONFIG.anthraciteMaxHeight)).spreadHorizontally().repeat(CONFIG.anthraciteVeinsPerkChunk));
//
//    /*
//     * Registers the configured features
//     */
//    private static <FC extends FeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF create(final String id, final CF feature) {
//        final Identifier featureId = new Identifier(GeoActivity.MODID, id);
//
//        if (BuiltinRegistries.CONFIGURED_FEATURE.getIds().contains(featureId)) {
//            GeoActivity.LOGGER.error("ConfiguredFeature ID: " + featureId + " already exists in registry.");
//        }
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, featureId, feature);
//        return feature;
//    }
//
//    /*
//     * Adds the features and biome modifications to the world gen.
//     */
//    public static void init() {
//        WORLD_GENERATOR.add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, LIGNITE_ORE));
//        WORLD_GENERATOR.add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, BITUMINOUS_ORE));
//        WORLD_GENERATOR.add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, ANTHRACITE_ORE));
//    }
}
