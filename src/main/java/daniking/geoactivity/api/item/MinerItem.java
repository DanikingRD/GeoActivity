package daniking.geoactivity.api.item;

import daniking.geoactivity.common.item.util.GAMiningToolItem;
import daniking.geoactivity.common.registry.GATags;
import net.minecraft.item.ToolMaterial;

/**
 * Base class for all miner items
 */
public abstract class MinerItem extends GAMiningToolItem implements Rechargeable {

    public MinerItem(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings) {
        super(attackDamage, attackSpeed, material, GATags.MINER_MINEABLE, settings);
    }
}
