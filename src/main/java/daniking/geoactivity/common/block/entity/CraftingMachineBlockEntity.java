package daniking.geoactivity.common.block.entity;

import daniking.geoactivity.client.gui.screen.handler.CraftingMachineScreenHandler;
import daniking.geoactivity.common.recipe.crafting.IMachineCraftingRecipe;
import daniking.geoactivity.common.registry.GABlockEntityTypes;
import daniking.geoactivity.common.registry.GARecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CraftingMachineBlockEntity extends GAMachineBlockEntity {

    private int progress;
    private final int outputIndex = 9;
    private int maxProgress;
    private final PropertyDelegate delegate;

    private CraftingInventory craftingInventory = null;
    private IMachineCraftingRecipe lastRecipe = null;

    public CraftingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(GABlockEntityTypes.CRAFTING_MACHINE, 10, pos, state);
        this.delegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CraftingMachineBlockEntity.this.progress;
                    case 1 -> CraftingMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CraftingMachineBlockEntity.this.progress = value;
                    case 1 -> CraftingMachineBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        writeNbt(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (this.world != null && world instanceof ServerWorld world)  {
            world.getChunkManager().markForUpdate(pos);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.progress = nbt.getShort("Progress");
        this.maxProgress = nbt.getShort("MaxProgress");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("Progress", (short)this.progress);
        nbt.putShort("MaxProgress", (short)this.maxProgress);
    }

    public static void tick(World world, CraftingMachineBlockEntity blockEntity) {
        if (world != null && !world.isClient) {
            final IMachineCraftingRecipe recipe = blockEntity.getCurrentRecipe();
            if (recipe == null) {
                blockEntity.progress = 0;
                return;
            }
            blockEntity.maxProgress = 100;
            if (blockEntity.progress >= blockEntity.maxProgress) {
                if (blockEntity.craft(recipe)) {
                    blockEntity.progress = 0;
                }
            } else {
                if (blockEntity.canCraft(recipe)) {
                    blockEntity.progress++;
                } else {
                    blockEntity.progress = 0;
                }
            }
        }
    }

    private boolean craft(IMachineCraftingRecipe recipe) {
        if (recipe == null) {
            return false;
        } else if (!canCraft(recipe)) {
            return false;
        } else {
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                final DefaultedList<Ingredient> ingredients = recipe.getIngredients();
                final Ingredient ingredient = ingredients.get(i);
                final ItemStack stack = this.getStack(i);
                if (ingredient.test(stack)) {
                    stack.decrement(1);
                } else {
                    //we repeat the operation
                    for (int j = 0; j < this.size()-1; j++) {
                        System.out.println(this.size()-1);
                        final ItemStack remainingStack = this.getStack(j);
                        if (ingredient.test(remainingStack)) {
                            remainingStack.decrement(1);
                            break;
                        }
                    }
                }
            }
            final ItemStack output = this.getStack(this.outputIndex);
            final ItemStack result = recipe.craft(updateCraftingInventory());
            if (output.isEmpty()) {
                this.setStack(this.outputIndex, result.copy());
            } else {
                output.increment(recipe.getOutput().getCount());
            }
            return true;
        }
    }

    @Nullable
    public IMachineCraftingRecipe getCurrentRecipe() {
        if (this.world == null) {
            return null;
        }
        final CraftingInventory crafting = updateCraftingInventory();
        if (crafting.isEmpty()) {
            return null;
        } else if (this.lastRecipe != null && lastRecipe.matches(crafting, world)) {
            return lastRecipe;
        } else {
            final Optional<IMachineCraftingRecipe> recipe = world.getRecipeManager().getFirstMatch(GARecipeTypes.MACHINE_CRAFTING_RECIPE_TYPE, crafting, this.world);
            if (recipe.isPresent()) {
                lastRecipe = recipe.get();
                return lastRecipe;
            }
            return null;

        }
    }

    public CraftingInventory updateCraftingInventory() {
        if (this.craftingInventory == null) {
            this.craftingInventory = new CraftingInventory(new ScreenHandler(null, -1) {
                @Override
                public boolean canUse(PlayerEntity player) {
                    return false;
                }
            }, 3, 3);
        }
        for (int i = 0; i < 9; i++) {
            this.craftingInventory.setStack(i, this.getStack(i));
        }
        return this.craftingInventory;
    }

    //checks whether the result stack count fits in the output space
    private boolean fitsOutputSpace(ItemStack output) {
        final ItemStack stack = this.getStack(this.outputIndex);
        if (stack.isEmpty()) {
            return true;
        } else if (ItemStack.areItemsEqualIgnoreDamage(stack, output)) {
            return stack.getMaxCount() > stack.getCount() + output.getCount();
        } else {
            return false;
        }
    }

    private boolean canCraft(IMachineCraftingRecipe recipe) {
        if (this.world == null) {
            return false;
        } else if (recipe == null) {
            return false;
        } else {
            final CraftingInventory crafting = this.updateCraftingInventory();
            if (crafting.isEmpty()) {
                return false;
            } else if (!recipe.matches(crafting, this.world)) {
                return false;
            } else if (!fitsOutputSpace(recipe.getOutput())){
                return false;
            } else {
                final DefaultedList<ItemStack> remainingStacks = recipe.getRemainder(crafting);
                for (var stack : remainingStacks) {
                    if (!stack.isEmpty())  {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    //no impl for SidedInventory
    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[] {};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CraftingMachineScreenHandler(syncId, inv, this, this.delegate);
    }
}
