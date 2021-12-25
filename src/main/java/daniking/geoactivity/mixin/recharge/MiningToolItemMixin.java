package daniking.geoactivity.mixin.recharge;

import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.util.RechargeUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MiningToolItem.class)
public class MiningToolItemMixin {

    @Inject(method = "postHit", at = @At("HEAD"), cancellable = true)
    private void setDestroyedOnEntityHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof Rechargeable) {
            RechargeUtil.initDestroyedNbt((PlayerEntity) attacker, stack);
            if (RechargeUtil.isDestroyed(stack)) {
                cir.setReturnValue(false);
            }
        }
    }
}
