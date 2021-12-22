package daniking.geoactivity.common.item.util;

import net.minecraft.block.Block;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.Tag;

public class GAMiningToolItem extends MiningToolItem {

    protected final float attackSpeed;

    protected GAMiningToolItem(float attackDamage, float attackSpeed, ToolMaterial material, Tag<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
        this.attackSpeed = attackSpeed;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }
}
