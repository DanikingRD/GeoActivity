package daniking.geoactivity.mixin.recharge;

import daniking.geoactivity.api.item.IAdvancedArmor;
import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.util.RechargeableHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Shadow
    @Final
    public DefaultedList<ItemStack> armor;

    @Shadow
    @Final
    public PlayerEntity player;

    @Inject(method = "damageArmor", at = @At("HEAD"), cancellable = true)
    private void returnDamage(DamageSource damageSource, float amount, int[] slots, CallbackInfo ci) {
        for (int i = 0; i < slots.length; i++) {
            final ItemStack stack = this.armor.get(i);
            if (stack.getItem() instanceof ArmorItem armor) {
                if (armor instanceof IAdvancedArmor advancedArmor) {
                    advancedArmor.onArmorDamage(stack, player, damageSource, amount, slots);
                }
                if (armor instanceof Rechargeable) {
                    if (RechargeableHelper.isDestroyed(stack)) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}