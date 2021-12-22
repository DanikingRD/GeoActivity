package daniking.geoactivity.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class GAMachineBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, SidedInventory {

    private final DefaultedList<ItemStack> inventory;

    public GAMachineBlockEntity(final BlockEntityType<?> type, final int size, final BlockPos pos, final BlockState state) {
        super(type, pos, state);
        this.inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
    }

    protected int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        return (AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(stack.getItem(), 0));
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

	public ItemStack removeStack(int slot, int amount) {
		return Inventories.splitStack(this.inventory, slot, amount);
	}

	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.inventory, slot);
	}

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}
		this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return canUse().test(player);
    }

    private Predicate<PlayerEntity> canUse() {
        return player -> (Objects.requireNonNull(this.getWorld()).getBlockEntity(this.getPos()) == this && player.getPos().distanceTo(Vec3d.of(this.getPos())) < 16);
    }

}
