package geoactivity.common.item.util;

import geoactivity.api.item.Charge;
import geoactivity.common.registry.GAObjects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

public class GAChargeItem extends Item implements Charge {

    public GAChargeItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getCharge() {
        if (this == GAObjects.LIGNITE_COAL) {
            return 100;
        } else if (this == GAObjects.BITUMINOUS_COAL) {
            return 200;
        } else if (this == GAObjects.ANTHRACITE_COAL) {
            return 300;
        } else {
            return 0;
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        if (stack.getItem() == GAObjects.ANTHRACITE_COAL) {
            return Rarity.UNCOMMON;
        } else {
            return super.getRarity(stack);
        }
    }
}
