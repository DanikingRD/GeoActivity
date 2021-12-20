package geoactivity.common.registry;

import geoactivity.GeoActivity;
import geoactivity.common.block.CoalRefinerBlock;
import geoactivity.common.block.CraftingMachineBlock;
import geoactivity.common.block.GAOreBlock;
import geoactivity.common.item.AutoStoneBuilderItem;
import geoactivity.common.item.GABookItem;
import geoactivity.common.item.ReinforcedMinerItem;
import geoactivity.common.item.advanced.AdvancedAxeItem;
import geoactivity.common.item.advanced.AdvancedPickaxeItem;
import geoactivity.common.item.advanced.AdvancedShovelItem;
import geoactivity.common.item.advanced.AdvancedSwordItem;
import geoactivity.common.item.advanced.armor.AdvancedArmorItem;
import geoactivity.common.item.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GAObjects {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static final Block LIGNITE_COAL_BLOCK = register("lignite_coal_block", new Block(settings(Material.STONE, 3.0F, 4.0F).requiresTool()));
    public static final Item LIGNITE_COAL = register("lignite_coal", new GAChargeItem(settings()));
    public static final Block BITUMINOUS_COAL_BLOCK = register("bituminous_coal_block", new Block(settings(Material.STONE, 5.0F, 6.0F).requiresTool()));
    public static final Item BITUMINOUS_COAL = register("bituminous_coal", new GAChargeItem(settings()));
    public static final Block ANTHRACITE_COAL_BLOCK = register("anthracite_coal_block", new Block(settings(Material.STONE, 5.0F, 6.0F).requiresTool()));
    public static final Item ANTHRACITE_COAL = register("anthracite_coal", new GAChargeItem(settings()));
    public static final Item GRAPHITE = register("graphite", new Item(settings()));
    public static final Item CARBON_FIBER = register("carbon_fiber", new Item(settings()));
    public static final Item CARBON_STICK = register("carbon_stick", new Item(settings()));
    public static final Item SMALL_PRECIOUS_ALLOY_INGOT = register("small_precious_alloy_ingot", new Item(settings().rarity(Rarity.UNCOMMON)));
    //ores
    public static final Block LIGNITE_ORE = register("lignite_ore", new GAOreBlock(settings(Material.STONE, 3.0F, 3.0F).requiresTool(), UniformIntProvider.create(0, 2)));
    public static final Block DEEPSLATE_LIGNITE_ORE = register("deepslate_lignite_ore", new GAOreBlock(settings(Material.STONE,  4.5F, 3.0F).requiresTool().sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(0, 2)));
    public static final Block BITUMINOUS_ORE = register("bituminous_ore", new GAOreBlock(settings(Material.STONE, 3.0F, 3.0F).requiresTool(), UniformIntProvider.create(2, 5)));
    public static final Block DEEPSLATE_BITUMINOUS_ORE = register("deepslate_bituminous_ore", new GAOreBlock(settings(Material.STONE,  4.5F, 3.0F).requiresTool().sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(2, 5)));
    public static final Block ANTHRACITE_ORE = register("anthracite_ore", new GAOreBlock(settings(Material.STONE, 3.0F, 3.0F).requiresTool(), UniformIntProvider.create(3, 7)));
    public static final Block DEEPSLATE_ANTHRACITE_ORE = register("deepslate_anthracite_ore", new GAOreBlock(settings(Material.STONE,  4.5F, 3.0F).requiresTool().sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(3, 7)));

    //misc
    public static final Block HARDENED_GLASS = register("hardened_glass", new GlassBlock(settings(Material.GLASS, 20.0F, 130.0F).requiresTool().nonOpaque()));
    public static final Block GRAPHITE_BASE = register("graphite_base", new GATransparentBlock(settings(Material.STONE, 3.0F, 15.0F).requiresTool().nonOpaque()));
    public static final Block HARDENED_BRICK = register("hardened_brick", new Block(settings(Material.STONE, 20.0F, 150.0F).requiresTool()));
    //reinforced
    public static final Item AUTO_STONE_BUILDER = register("auto_stone_builder", new AutoStoneBuilderItem(settings().maxDamage(1000)));
    public static final Item REINFORCED_SWORD = register("reinforced_sword", new SwordItem(GAMaterials.REINFORCED_TOOL, 3, -2.4F,settings()));
    public static final Item REINFORCED_PICKAXE = register("reinforced_pickaxe", new GAPickaxeItem(GAMaterials.REINFORCED_TOOL, 1, -2.8F,settings()));
    public static final Item REINFORCED_AXE = register("reinforced_axe", new GAAxeItem(GAMaterials.REINFORCED_TOOL, 5.0F, -3.1F, settings()));
    public static final Item REINFORCED_SHOVEL = register("reinforced_shovel", new ShovelItem(GAMaterials.REINFORCED_TOOL,1.5F, -3.0F,settings()));
    public static final Item REINFORCED_HOE = register("reinforced_hoe", new GAHoeItem(GAMaterials.REINFORCED_TOOL, -3, 0.0F, settings()));
    public static final Item REINFORCED_MINER = register("reinforced_miner", new ReinforcedMinerItem(2.0F, -3.0F, GAMaterials.REINFORCED_TOOL, settings()));
    public static final Item ADVANCED_SWORD = register("advanced_sword", new AdvancedSwordItem(GAMaterials.ADVANCED_TOOL, 3, -2.4F,settings()));
    public static final Item ADVANCED_PICKAXE = register("advanced_pickaxe", new AdvancedPickaxeItem(GAMaterials.ADVANCED_TOOL, 1, -2.8F,settings()));
    public static final Item ADVANCED_AXE = register("advanced_axe", new AdvancedAxeItem(GAMaterials.ADVANCED_TOOL, 5.0F, -3.0F,settings()));
    public static final Item ADVANCED_SHOVEL = register("advanced_shovel", new AdvancedShovelItem(GAMaterials.ADVANCED_TOOL, 1.5F, -3.0F,settings()));
    //public static final Item ADVANCED_HOE = register("advanced_hoe", new AdvancedHoeItem(GAMaterials.ADVANCED_TOOL, -3, 0.0F,settings()));
    public static final Item REINFORCED_HELMET = register("reinforced_helmet", new ArmorItem(GAMaterials.REINFORCED_ARMOR, EquipmentSlot.HEAD, settings()));
    public static final Item REINFORCED_CHESTPLATE = register("reinforced_chestplate", new ArmorItem(GAMaterials.REINFORCED_ARMOR, EquipmentSlot.CHEST, settings()));
    public static final Item REINFORCED_LEGGINGS = register("reinforced_leggings", new ArmorItem(GAMaterials.REINFORCED_ARMOR, EquipmentSlot.LEGS, settings()));
    public static final Item REINFORCED_BOOTS = register("reinforced_boots", new ArmorItem(GAMaterials.REINFORCED_ARMOR, EquipmentSlot.FEET, settings()));
    public static final Item ADVANCED_HELMET = register("advanced_helmet", new AdvancedArmorItem(GAMaterials.ADVANCED_ARMOR, EquipmentSlot.HEAD, settings()));
    public static final Item ADVANCED_CHESTPLATE = register("advanced_chestplate", new AdvancedArmorItem(GAMaterials.ADVANCED_ARMOR, EquipmentSlot.CHEST, settings()));
    public static final Item ADVANCED_LEGGINGS = register("advanced_leggings", new AdvancedArmorItem(GAMaterials.ADVANCED_ARMOR, EquipmentSlot.LEGS, settings()));
    public static final Item ADVANCED_BOOTS = register("advanced_boots", new AdvancedArmorItem(GAMaterials.ADVANCED_ARMOR, EquipmentSlot.FEET, settings()));
    //machines
    public static final Block COAL_REFINER = register("coal_refiner", new CoalRefinerBlock(settings(Material.METAL, 3.5F, 15.0F).requiresTool().sounds(BlockSoundGroup.STONE).luminance(state -> state.get(CoalRefinerBlock.LIT) ? 13: 0)));
    public static final Block CRAFTING_MACHINE = register("crafting_machine", new CraftingMachineBlock(settings(Material.METAL, 3.0F, 15.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    //book
    public static final Item GEOACTIVITY_BOOK = register("geoactivity_book", new GABookItem(settings().maxCount(1)));

    private static <T extends Item> T register (final String id, final T item) {
        ITEMS.put(new Identifier(GeoActivity.MODID, id), item);
    
        return item;
    }

    private static <T extends Block> T register (final String id, final T block) {
        final Identifier path = new Identifier(GeoActivity.MODID, id);
        BLOCKS.put(path, block);
        ITEMS.put(path, new BlockItem(block, settings()));
        return block;
    }

    private static <T extends Item> Item.Settings settings() {
        return new Item.Settings().group(GeoActivity.GEOACTIVITY_GROUP);
    }

    private static <T extends Block> AbstractBlock.Settings settings(final Material material, final float hardness, final float resistance) {
        return AbstractBlock.Settings.of(material).strength(hardness, resistance);
    }

    public static void init() {
        ITEMS.keySet().forEach(id -> Registry.register(Registry.ITEM, id, ITEMS.get(id)));
        BLOCKS.keySet().forEach(id -> Registry.register(Registry.BLOCK, id, BLOCKS.get(id)));
    }
}
