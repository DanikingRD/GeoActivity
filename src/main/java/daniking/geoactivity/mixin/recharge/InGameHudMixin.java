package daniking.geoactivity.mixin.recharge;

import daniking.geoactivity.common.item.advanced.armor.AdvancedArmorItem;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {


    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"), cancellable = true)
    private void renderArmor(MatrixStack matrices, CallbackInfo ci) {

    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getArmor()I"))
    private int injected(PlayerEntity playerEntity) {
        for (final ItemStack stack : playerEntity.getArmorItems()) {
            if (stack.getItem() instanceof ArmorItem armor) {
                if (armor instanceof AdvancedArmorItem advancedArmorItem) {
                    return advancedArmorItem.getArmorProtection(stack, playerEntity);
                }
                return playerEntity.getArmor();
            }
        }
        return 0;
    }
}
