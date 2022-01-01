package daniking.geoactivity.common.item.advanced;

import daniking.geoactivity.api.item.IUpgradeableTierI;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedSwordItem extends SwordItem implements IUpgradeableTierI {

    public AdvancedSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}
