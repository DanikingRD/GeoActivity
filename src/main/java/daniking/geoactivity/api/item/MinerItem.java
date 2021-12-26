package daniking.geoactivity.api.item;

import daniking.geoactivity.common.item.util.GAMiningToolItem;
import daniking.geoactivity.common.registry.GATags;
import daniking.geoactivity.common.util.RechargeUtil;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

/**
 * Base class for all miner items
 */
public abstract class MinerItem extends GAMiningToolItem implements Rechargeable {

    public MinerItem(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings) {
        super(attackDamage, attackSpeed, material, GATags.MINER_MINEABLE, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null) {
            return ActionResult.PASS;
        }
        RechargeUtil.initDestroyedNbt(context.getPlayer(), context.getStack());
        if (RechargeUtil.isFatigued(context.getStack())) {
            return ActionResult.PASS;
        }
        return Items.DIAMOND_SHOVEL.useOnBlock(context);
    }
}
