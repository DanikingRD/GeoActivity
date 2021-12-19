package geoactivity.client.gui.screen.handler.slot;

import geoactivity.common.block.entity.SmelterBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class GAOutputSlot extends Slot {

    private final Inventory inventory;
    private final PlayerEntity player;
    private final World world;
    private int amount;

    public GAOutputSlot(PlayerEntity player, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.inventory = inventory;
        this.player = player;
        this.world = player.getEntityWorld();
    }

    @Override
    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            this.amount += Math.min(amount, this.getStack().getCount());
        }

        return super.takeStack(amount);
    }
    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        this.onCrafted(stack);
        super.onTakeItem(player, stack);
    }

    @Override
    protected void onCrafted(ItemStack stack, int amount) {
        this.amount += amount;
        this.onCrafted(stack);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    public void onCrafted(ItemStack stack) {
        stack.onCraft(this.player.world, this.player, this.amount);
        if (this.world instanceof ServerWorld world && this.inventory instanceof SmelterBlockEntity entity) {
            entity.dropExperience(world, player.getPos());
        }
        this.amount = 0;
    }
}
