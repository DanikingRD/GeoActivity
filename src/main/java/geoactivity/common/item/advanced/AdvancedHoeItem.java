package geoactivity.common.item.advanced;

import geoactivity.api.item.IUpgradeable;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

//TODO: ADD the advanced hoe
public class AdvancedHoeItem extends HoeItem implements IUpgradeable {
    public AdvancedHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
