package daniking.geoactivity.common.registry;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public final class GAMaterials {

    public static final ArmorMaterial ADVANCED_ARMOR = new ArmorMaterial() {

        @Override
        public int getDurability(EquipmentSlot slot) {
            int[] durability = new int[]{13, 15, 16, 11};
            return durability[slot.getEntitySlotId()] * 35;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            final int [] protection = new int[] {4, 7, 10, 4};
            return protection[slot.getEntitySlotId()];
        }

        @Override
        public int getEnchantability() {
            return 8;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return "advanced";
        }

        @Override
        public float getToughness() {
            return 2.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    public static final ToolMaterial ADVANCED_TOOL = new ToolMaterial() {
        @Override
        public int getDurability() {
            return 1000;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 10.0F;
        }

        @Override
        public float getAttackDamage() {
            return 5.0F;
        }

        @Override
        public int getMiningLevel() {
            return 3;
        }

        @Override
        public int getEnchantability() {
            return 8;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    };

    public static final ArmorMaterial REINFORCED_ARMOR = new ArmorMaterial() {
        @Override
        public int getDurability(EquipmentSlot slot) {
            return ArmorMaterials.IRON.getDurability(slot) * 2;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            return ArmorMaterials.IRON.getProtectionAmount(slot);
        }

        @Override
        public int getEnchantability() {
            return 18;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return "reinforced";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    public static final ToolMaterial REINFORCED_TOOL = new ToolMaterial() {
        @Override
        public int getDurability() {
            return ToolMaterials.IRON.getDurability() * 2;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return ToolMaterials.DIAMOND.getMiningSpeedMultiplier();
        }

        @Override
        public float getAttackDamage() {
            return ToolMaterials.DIAMOND.getAttackDamage();
        }

        @Override
        public int getMiningLevel() {
            return ToolMaterials.IRON.getMiningLevel();
        }

        @Override
        public int getEnchantability() {
            return (ToolMaterials.IRON.getEnchantability() + ToolMaterials.GOLD.getEnchantability()) / 2;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(GAObjects.REINFORCED_INGOT);
        }
    };
}
