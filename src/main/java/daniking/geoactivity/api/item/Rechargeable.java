package daniking.geoactivity.api.item;

import com.google.common.collect.Multimap;
import daniking.geoactivity.api.gui.handler.ItemScreenHandler;
import daniking.geoactivity.common.item.util.GAMiningToolItem;
import daniking.geoactivity.common.util.GAInventory;
import daniking.geoactivity.common.util.RechargeUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public interface Rechargeable {

     UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
     UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    /**
     * Nbt key used whenever
     * the tool is fatigued
     */
    String FATIGUED_NBT_KEY = "fatigued";

    /**
     * Charge slot index for all
     * rechargeable items.
     */
    int CHARGE_SLOT_INDEX = 0;

    /**
     * Gets the gui of the item.
     * @return ScreenHandler.
     */
    ItemScreenHandler getGui(final int syncId, final PlayerInventory playerInventory, final GAInventory inventory);

    /**
     * Gets the size of the inventory.
     * @return int.
     */
    int inventorySize();

    /**
     * Removes and puts back stack attributes modifier
     * whenever the item gets destroyed or charged.
     * @param slot EquipmentSlot
     * @param stack ItemStack
     * @param attributes MultiMap<>
     */
    default void handleAttributesModifiers(final EquipmentSlot slot, final ItemStack stack, final Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        if (slot == EquipmentSlot.MAINHAND) {
           if (RechargeUtil.isFatigued(stack)) {
                attributes.removeAll(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                attributes.removeAll(EntityAttributes.GENERIC_ATTACK_SPEED);
                return;
            }
            if (stack.getItem() instanceof GAMiningToolItem miner) {
                if (attributes.isEmpty() && !RechargeUtil.isFatigued(stack)) {
                    attributes.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", miner.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
                    attributes.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", miner.getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
                }
            }
        }
    }
}
