package daniking.geoactivity.common.item.advanced;

import daniking.geoactivity.api.item.IUpgradeable;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedAxeItem extends AxeItem implements IUpgradeable {
    public AdvancedAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
