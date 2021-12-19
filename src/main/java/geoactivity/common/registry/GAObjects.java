package geoactivity.common.registry;

import geoactivity.common.GeoActivity;
import geoactivity.common.block.CoalRefinerBlock;
import geoactivity.common.block.CraftingMachineBlock;
import geoactivity.common.block.GAOreBlock;
import geoactivity.common.item.AutoStoneBuilderItem;
import geoactivity.common.item.ReinforcedMinerItem;
import geoactivity.common.item.advanced.AdvancedAxeItem;
import geoactivity.common.item.advanced.AdvancedPickaxeItem;
import geoactivity.common.item.advanced.AdvancedShovelItem;
import geoactivity.common.item.advanced.AdvancedSwordItem;
import geoactivity.common.item.advanced.armor.AdvancedArmorItem;
import geoactivity.common.item.util.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Contains all the objects (items and blocks) of "GeoActivity".
 */
public final class GAObjects {
    
    /*
     * Done linked maps to maintain insertion order.
     */
    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static final Item LIGNITE_COAL = register("lignite_coal", new GAChargeItem(settings()));
    public static final Item BITUMINOUS_COAL = register("bituminous_coal", new GAChargeItem(settings()));
    public static final Item ANTHRACITE_COAL = register("anthracite_coal", new GAChargeItem(settings()));
    public static final Item GRAPHITE = register("graphite", new Item(settings()));
    public static final Item CARBON_FIBER = register("carbon_fiber", new Item(settings()));
    public static final Item CARBON_STICK = register("carbon_stick", new Item(settings()));
    public static final Item SMALL_PRECIOUS_ALLOY_INGOT = register("small_precious_alloy_ingot", new Item(settings().rarity(Rarity.UNCOMMON)));
    //perks

    public static final Block LIGNITE_ORE = register("lignite_ore", new GAOreBlock(settings(Material.STONE, 3.0F, 15.0F).requiresTool(), UniformIntProvider.create(1, 3)));
    public static final Block BITUMINOUS_ORE = register("bituminous_ore", new GAOreBlock(settings(Material.STONE, 3.0F, 15.0F).requiresTool(), UniformIntProvider.create(2, 5)));
    public static final Block ANTHRACITE_ORE = register("anthracite_ore", new GAOreBlock(settings(Material.STONE, 3.0F, 15.0F).requiresTool(), UniformIntProvider.create(3, 7)));
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

    /**
     * Inserts the item (value) into the map which its identifier (key).
     * @param <T> Item.
     * @param id String containing the name of the item.
     * @param item Item instance.
     * @return the item.
     */
    private static <T extends Item> T register (final String id, final T item) {
        ITEMS.put(new Identifier(GeoActivity.MODID, id), item);
    
        return item;
    }

    /**
     * Inserts the block (value) into the map which its identifier (key).
     * @param <T> Block.
     * @param id String containing the name of the block.
     * @param block Block instance.
     * @return the block.
     */
    private static <T extends Block> T register (final String id, final T block) {
        final Identifier path = new Identifier(GeoActivity.MODID, id);
        BLOCKS.put(path, block);
        ITEMS.put(path, new BlockItem(block, settings()));
        return block;
    }

    /**
     * Creates the settings for your item.
     * @param <T> Item
     * @return the item settings
     */
    private static <T extends Item> Item.Settings settings() {
        return new Item.Settings().group(GeoActivity.GEOACTIVITY_GROUP);
    }

    /**
     * Creates the settings for your block.
     * @param <T> Block
     * @param material Block material
     * @param hardness how hard it is to break.
     * @param resistance how hard it is to blow up within a explosion.
     * @return the block settings
     */
    private static <T extends Block> AbstractBlock.Settings settings(final Material material, final float hardness, final float resistance) {
        return AbstractBlock.Settings.of(material).strength(hardness, resistance);
    }

    /**
     * Registers all the objects
     */
    public static void init() {
        ITEMS.keySet().forEach(id -> Registry.register(Registry.ITEM, id, ITEMS.get(id)));
        BLOCKS.keySet().forEach(id -> Registry.register(Registry.BLOCK, id, BLOCKS.get(id)));
    }
}
