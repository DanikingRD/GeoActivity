package geoactivity.common.block.entity;

import geoactivity.client.gui.screen.handler.CoalRefinerScreenHandler;
import geoactivity.common.block.CoalRefinerBlock;
import geoactivity.common.item.util.GAChargeItem;
import geoactivity.common.recipe.RefinementRecipe;
import geoactivity.common.registry.GABlockEntityTypes;
import geoactivity.common.registry.GAObjects;
import geoactivity.common.registry.GARecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CoalRefinerBlockEntity extends SmelterBlockEntity {
    
    private final PropertyDelegate propertyDelegate;
    private final int fuelSlot = 0;
    private final int inputSlot = 1;
    private final int outputSlot = 2;

    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;
    private float experience;
    private int experienceMultiplier;
    
    public CoalRefinerBlockEntity(BlockPos pos, BlockState state) {
        super(GABlockEntityTypes.COAL_REFINER, 3, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CoalRefinerBlockEntity.this.burnTime;
                    case 1 -> CoalRefinerBlockEntity.this.fuelTime;
                    case 2 -> CoalRefinerBlockEntity.this.cookTime;
                    case 3 -> CoalRefinerBlockEntity.this.cookTimeTotal;
                    default -> 0;
                };
            }
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CoalRefinerBlockEntity.this.burnTime = value;
                    case 1 -> CoalRefinerBlockEntity.this.fuelTime = value;
                    case 2 -> CoalRefinerBlockEntity.this.cookTime = value;
                    case 3 -> CoalRefinerBlockEntity.this.cookTimeTotal = value;
                }
            }
            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        final Item item = stack.getItem();

        if (item == GAObjects.LIGNITE_COAL) {
            return 3200;
        }
        if (item == GAObjects.BITUMINOUS_COAL) {
            return 6400;
        }
        if (item == GAObjects.ANTHRACITE_COAL) {
            return 9600;
        }
        return super.getItemBurnTime(stack);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getItemBurnTime(this.getStack(fuelSlot));
        this.experience = nbt.getFloat("Experience");
        this.experienceMultiplier = nbt.getByte("ExperienceMultiplier");

    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putShort("CookTime", (short)this.cookTime);
        nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        nbt.putFloat("Experience", this.experience);
        nbt.putByte("ExperienceMultiplier", (byte) this.experienceMultiplier);;
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public static void tick(World world, BlockPos pos, BlockState state, CoalRefinerBlockEntity blockEntity) {
        if (world == null) {
            return;
        }
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
            final RefinementRecipe recipe = world.getRecipeManager()
                    .listAllOfType(GARecipeTypes.REFINEMENT_RECIPE_TYPE)
                    .stream()
                    .filter(refinementRecipe -> refinementRecipe.input().test(blockEntity.getStack(blockEntity.inputSlot)))
                    .findFirst()
                    .orElse(null);

            if (recipe != null) {
                blockEntity.cookTimeTotal = recipe.time();
                if (!blockEntity.isBurning() && canSmelt(blockEntity, recipe)) {
                    blockEntity.burnTime = blockEntity.getItemBurnTime(blockEntity.getStack(blockEntity.fuelSlot));
                    blockEntity.fuelTime = blockEntity.burnTime;
                    if (blockEntity.isBurning()) {
                        dirty = true;
                        final ItemStack fuelStack = blockEntity.getStack(blockEntity.fuelSlot);
                        if(fuelStack.getItem().hasRecipeRemainder()) {
                            blockEntity.setStack(blockEntity.fuelSlot, new ItemStack(fuelStack.getItem().getRecipeRemainder()));
                        } else if (fuelStack.getCount() > 1) {
                            fuelStack.decrement(1);
                        } else if (fuelStack.getCount() == 1) {
                            blockEntity.setStack(blockEntity.fuelSlot, ItemStack.EMPTY);
                        }
                    }
                }

                if (blockEntity.isBurning() && canSmelt(blockEntity, recipe)) {
                    ++blockEntity.cookTime;
                    if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                        blockEntity.cookTime = 0;
                        blockEntity.cookTimeTotal = recipe.time();
                        smeltItem(blockEntity, recipe);
                        dirty = true;
                    }
                }
                world.setBlockState(pos, state.with(CoalRefinerBlock.LIT, blockEntity.isBurning()), Block.NOTIFY_ALL);
                if (dirty) {
                    blockEntity.markDirty();
                }
            } else {
                blockEntity.cookTime = 0;
            }
        }
    }

    private static void smeltItem(final CoalRefinerBlockEntity blockEntity, final RefinementRecipe recipe) {
        if (recipe == null) {
            return;
        }
        if (!canSmelt(blockEntity, recipe)) {
            return;
        }
        final ItemStack recipeOutput = recipe.getOutput().copy();
        final ItemStack outputStack = blockEntity.getStack(blockEntity.outputSlot);
        if (outputStack.isEmpty()) {
            blockEntity.setStack(blockEntity.outputSlot, recipeOutput);
        } else if (outputStack.isOf(recipeOutput.getItem())) {
            outputStack.increment(recipeOutput.getCount());
        }
        blockEntity.experience = recipe.experience();
        blockEntity.experienceMultiplier += recipe.getOutput().getCount();
        blockEntity.getStack(blockEntity.inputSlot).decrement(1);

    }

    private static boolean canSmelt(final CoalRefinerBlockEntity blockEntity, final RefinementRecipe recipe) {
        if (recipe == null) {
            return false;
        }
        if (blockEntity.getStack(blockEntity.inputSlot).isEmpty()) {
            return false;
        }
        final ItemStack outputStack = blockEntity.getStack(blockEntity.outputSlot);
        if (outputStack.isEmpty()) {
            return true;
        }
        final ItemStack recipeOutput = recipe.getOutput();
        if (!outputStack.isItemEqualIgnoreDamage(recipeOutput)) {
            return false;
        }
        int nextCount = outputStack.getCount() + recipeOutput.getCount();
        return (nextCount <= blockEntity.getMaxCountPerStack() && nextCount <= recipeOutput.getMaxCount());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CoalRefinerScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[] {0, 1, 2};
    }
    //TODO: use tags for inserting stuff.

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        if (slot == 0) {
            return this.getItemBurnTime(stack) > 0;
        }
        if (slot == 1) {
            return stack.getItem() instanceof GAChargeItem || stack.getItem() == Items.COAL;
        }
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 2;
    }


    @Override
    public void dropExperience(ServerWorld world, Vec3d playerPos) {
        if (this.experienceMultiplier > 0) {
            calculateAndDropExperience(world, playerPos, this.experienceMultiplier, this.experience);
        }
        this.experienceMultiplier = 0;
    }
}
