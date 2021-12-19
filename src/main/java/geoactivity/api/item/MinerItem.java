package geoactivity.api.item;

import geoactivity.common.item.util.GAMiningToolItem;
import geoactivity.common.registry.GATags;
import net.minecraft.item.ToolMaterial;

/**
 * Base class for all miner items,
 * which inherits from mining tool item and rechargeable interface.
 */
public abstract class MinerItem extends GAMiningToolItem implements Rechargeable {

    public MinerItem(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings) {
        super(attackDamage, attackSpeed, material, GATags.MINER_MINEABLE, settings);
    }
}
