package daniking.geoactivity.mixin.recharge;

import daniking.geoactivity.api.item.Rechargeable;
import daniking.geoactivity.common.util.RechargeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin{

    @Shadow protected abstract boolean isIn(ItemGroup group);

    @Shadow public abstract ItemStack getDefaultStack();

    @Inject(method = "canMine", at = @At("HEAD"), cancellable = true)
    private void setDestroyedOnMine(BlockState state, World world, BlockPos pos, PlayerEntity miner, CallbackInfoReturnable<Boolean> cir) {
        final ItemStack stack = miner.getMainHandStack();
        if (stack.getItem() instanceof Rechargeable) {
            RechargeUtil.initDestroyedNbt(miner, stack);
            if (RechargeUtil.isDestroyed(stack)) {
                cir.setReturnValue(false);
            }
        }
    }
    @Inject(method = "appendStacks", at = @At("HEAD"), cancellable = true)
    private void appendNotChargedStacks(ItemGroup group, DefaultedList<ItemStack> list, CallbackInfo ci) {
        if (this.isIn(group) && this instanceof Rechargeable) {
            final ItemStack stack = this.getDefaultStack();
            stack.setDamage(stack.getMaxDamage() - 2);
            RechargeUtil.setDestroyed(stack);
            list.add(stack);
            list.add(this.getDefaultStack());
            ci.cancel();
        }
    }
}
