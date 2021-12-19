package geoactivity.common.item.advanced;

import geoactivity.api.item.IUpgradeable;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedPickaxeItem extends PickaxeItem implements IUpgradeable {

    public AdvancedPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
