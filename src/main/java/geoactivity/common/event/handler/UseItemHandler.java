package geoactivity.common.event.handler;

import geoactivity.api.GeoActivityAPI;
import geoactivity.api.item.Rechargeable;
import geoactivity.common.util.GAInventory;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class UseItemHandler implements UseItemCallback {

    @Override
    public TypedActionResult<ItemStack> interact(PlayerEntity player, World world, Hand hand) {
        final ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            if (stack.getItem() instanceof Rechargeable item) {

                final GAInventory inventory = GAInventory.create(stack, item.size());
                if (player.isSneaking()) {
                    player.openHandledScreen(new NamedScreenHandlerFactory() {
                        @Override
                        public Text getDisplayName() {
                            return new TranslatableText(stack.getTranslationKey());
                        }

                        @Nullable
                        @Override
                        public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                            return item.getGui(syncId, inv, inventory);
                        }
                    });
                } else {
                    GeoActivityAPI.charge(stack);
                }
            }
        }
        return new TypedActionResult<>(ActionResult.PASS, stack);
    }
}
