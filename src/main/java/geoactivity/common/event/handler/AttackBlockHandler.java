package geoactivity.common.event.handler;

import geoactivity.api.item.Rechargeable;
import geoactivity.common.util.RechargeableHelper;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class AttackBlockHandler implements AttackBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        final ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof Rechargeable) {
            RechargeableHelper.initDestroyedNbt(player, stack);
            return ActionResult.PASS;
        }
        return ActionResult.PASS;
    }
}
