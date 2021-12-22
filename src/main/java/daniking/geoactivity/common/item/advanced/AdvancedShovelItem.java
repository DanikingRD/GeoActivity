package daniking.geoactivity.common.item.advanced;

import daniking.geoactivity.api.item.IUpgradeable;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedShovelItem extends ShovelItem implements IUpgradeable {
    public AdvancedShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
