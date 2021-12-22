package daniking.geoactivity.common.registry;

import daniking.geoactivity.GeoActivity;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public final class GATags {

    public static final Tag<Block> MINER_MINEABLE = TagFactory.BLOCK.create(new Identifier(GeoActivity.MODID, "mineable/miner"));
    public static final Tag<Item> CHARGE = TagFactory.ITEM.create(new Identifier(GeoActivity.MODID, "charge"));
}
