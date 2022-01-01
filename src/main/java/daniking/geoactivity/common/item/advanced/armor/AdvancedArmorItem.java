package daniking.geoactivity.common.item.advanced.armor;

import daniking.geoactivity.api.item.IAdvancedArmor;
import daniking.geoactivity.api.item.IUpgradeableTierI;
import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.util.GAInventory;
import daniking.geoactivity.common.util.RechargeUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AdvancedArmorItem extends ArmorItem implements IUpgradeableTierI, IAdvancedArmor {

    public AdvancedArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void onArmorDamage(ItemStack stack, PlayerEntity player, DamageSource source, float amount, int[] slots) {
        RechargeUtil.initDestroyedNbt(player, stack);
    }

    @Override
    public int getArmorProtection(ItemStack equipment, PlayerEntity player) {
        if (RechargeUtil.isFatigued(equipment)) {
            return player.getArmor() - this.getProtection();
        }
        return IAdvancedArmor.super.getArmorProtection(equipment, player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final ItemStack stack = user.getStackInHand(hand);
        final GAInventory inventory = GAInventory.create(stack, 2);
        if (user.isSneaking() || !inventory.getStack(Rechargeable.CHARGE_SLOT_INDEX).isEmpty()) {
            return new TypedActionResult<>(ActionResult.PASS, stack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public int getProtection() {
        return super.getProtection();
    }

    @Override
    public ArmorMaterial getMaterial() {
        return super.getMaterial();
    }
}
