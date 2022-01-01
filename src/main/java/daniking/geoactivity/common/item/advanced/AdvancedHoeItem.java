package daniking.geoactivity.common.item.advanced;

import daniking.geoactivity.api.item.IUpgradeableTierI;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

//TODO: ADD the advanced hoe
public class AdvancedHoeItem extends HoeItem implements IUpgradeableTierI {
    public AdvancedHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
