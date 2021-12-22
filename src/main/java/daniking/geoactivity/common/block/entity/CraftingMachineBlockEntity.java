package daniking.geoactivity.common.block.entity;

import daniking.geoactivity.client.gui.screen.handler.CraftingMachineScreenHandler;
import daniking.geoactivity.common.recipe.crafting.CraftingMachineSmeltingRecipe;
import daniking.geoactivity.common.registry.GABlockEntityTypes;
import daniking.geoactivity.common.registry.GAObjects;
import daniking.geoactivity.common.registry.GARecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CraftingMachineBlockEntity extends GAMachineBlockEntity {

    private final PropertyDelegate propertyDelegate;
    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;

    private final int fuelSlot = 0;
    private final int inputSlot = 1;
    private final int perkSlot = 2;

    private final CraftingResultInventory resultInventory = new CraftingResultInventory();

    public CraftingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(GABlockEntityTypes.CRAFTING_MACHINE, 3, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CraftingMachineBlockEntity.this.burnTime;
                    case 1 -> CraftingMachineBlockEntity.this.fuelTime;
                    case 2 -> CraftingMachineBlockEntity.this.cookTime;
                    case 3 -> CraftingMachineBlockEntity.this.cookTimeTotal;
                    default -> 0;
                };
            }
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CraftingMachineBlockEntity.this.burnTime = value;
                    case 1 -> CraftingMachineBlockEntity.this.fuelTime = value;
                    case 2 -> CraftingMachineBlockEntity.this.cookTime = value;
                    case 3 -> CraftingMachineBlockEntity.this.cookTimeTotal = value;
                }
            }
            @Override
            public int size() {
                return 4;
            }
        };
    }

    protected int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        final Item item = stack.getItem();
        if (item == Items.COAL_BLOCK) {
            return 80;
        }
        if (item == GAObjects.LIGNITE_COAL) {
            return 160;
        }
        if (item == GAObjects.BITUMINOUS_COAL) {
            return 320;
        }
        if (item == GAObjects.ANTHRACITE_COAL) {
            return 480;
        }
        return 0;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getItemBurnTime(this.getStack(fuelSlot));

    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putShort("CookTime", (short)this.cookTime);
        nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public static void tick(World world, BlockPos pos, BlockState state, CraftingMachineBlockEntity blockEntity) {
        if (!world.isClient) {
            boolean dirty = false;
            if (blockEntity.isBurning()) {
                --blockEntity.burnTime;
            } else {
                //if no fuel remaining, decrement progress
                if (blockEntity.cookTime > 0) {
                    blockEntity.cookTime = MathHelper.clamp((blockEntity.cookTime - 2), 0, blockEntity.cookTimeTotal);
                }
            }
            final CraftingMachineSmeltingRecipe type = world.getRecipeManager()
                    .listAllOfType(GARecipeTypes.CRAFTING_MACHINE_SMELTING_RECIPE_TYPE)
                    .stream()
                    .filter(recipe -> recipe.input().test(blockEntity.getStack(blockEntity.inputSlot)) && recipe.perk().test(blockEntity.getStack(blockEntity.perkSlot)))
                    .findFirst()
                    .orElse(null);
            if (type != null) {
                blockEntity.cookTimeTotal = type.time();
                if (!blockEntity.isBurning() && canUpgrade(blockEntity, type)) {
                    blockEntity.burnTime = blockEntity.getItemBurnTime(blockEntity.getStack(blockEntity.fuelSlot));
                    blockEntity.fuelTime = blockEntity.burnTime;
                    if (blockEntity.isBurning()) {
                        dirty = true;
                        final ItemStack fuelStack = blockEntity.getStack(blockEntity.fuelSlot);
                        if (fuelStack.getItem().hasRecipeRemainder()) {
                            blockEntity.setStack(blockEntity.fuelSlot, new ItemStack(fuelStack.getItem().getRecipeRemainder()));
                        } else if (fuelStack.getCount() > 1) {
                            fuelStack.decrement(1);
                        } else if (fuelStack.getCount() == 1) {
                            blockEntity.setStack(blockEntity.fuelSlot, ItemStack.EMPTY);
                        }
                    }
                }
                if (blockEntity.isBurning() && canUpgrade(blockEntity, type)) {
                    ++blockEntity.cookTime;
                    if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                        blockEntity.cookTime = 0;
                        blockEntity.cookTimeTotal = type.time();
                        doUpgrade(blockEntity, type);
                        dirty = true;
                    }
                }
                if (dirty) {
                    blockEntity.markDirty();
                }
            } else {
                blockEntity.cookTime = 0;
            }
        }
    }

    private static boolean canUpgrade(CraftingMachineBlockEntity entity, CraftingMachineSmeltingRecipe recipe) {
        if (entity == null || recipe == null) {
            return false;
        }
        final ItemStack inputStack = entity.getStack(entity.inputSlot);
        final ItemStack perkStack = entity.getStack(entity.perkSlot);
        if (inputStack.isEmpty() || perkStack.isEmpty()) {
            return false;
        }
        final ItemStack outputStack = entity.getResultInventory().getStack(0);
        return outputStack.isEmpty();
    }

    private static void doUpgrade(CraftingMachineBlockEntity entity, CraftingMachineSmeltingRecipe recipe) {
        if (recipe == null || !canUpgrade(entity, recipe)) {
            return;
        }
        final ItemStack recipeOutput = recipe.getOutput().copy();
        final ItemStack outputStack = entity.getResultInventory().getStack(0);
        if (outputStack.isEmpty()) {
            entity.getResultInventory().setStack(0, recipeOutput);
        }
        entity.getStack(entity.inputSlot).decrement(1);
        entity.getStack(entity.perkSlot).decrement(1);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CraftingMachineScreenHandler(syncId, inv, this, this.resultInventory, this.propertyDelegate, ScreenHandlerContext.create(this.world, this.pos));
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    public CraftingResultInventory getResultInventory() {
        return resultInventory;
    }
}
