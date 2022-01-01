package daniking.geoactivity.common.block.entity;

import daniking.geoactivity.client.gui.screen.handler.AdvancedCoalRefinerScreenHandler;
import daniking.geoactivity.common.block.AdvancedCoalRefinerBlock;
import daniking.geoactivity.common.recipe.RefinementRecipe;
import daniking.geoactivity.common.registry.GABlockEntityTypes;
import daniking.geoactivity.common.registry.GAObjects;
import daniking.geoactivity.common.registry.GARecipeTypes;
import daniking.geoactivity.common.registry.GATags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AdvancedCoalRefinerBlockEntity extends GAMachineBlockEntity {

    private int[][] tempNeighbors;
    private int[][] tempNeighbors2;

    private BlockPos masterPos = null;
    private boolean isValid = false;
    private boolean isMaster = false;

    private final int fuelSlot = 0;
    private final int firstInputSlot = 1;
    private final int secondInputSlot = 2;
    private final int firstOutputSlot = 3;
    private final int secondOutputSlot = 4;

    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;

    private final PropertyDelegate delegate;
    public AdvancedCoalRefinerBlockEntity(BlockPos pos, BlockState state) {
        super(GABlockEntityTypes.ADVANCED_COAL_REFINER, 5, pos, state);
        this.delegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AdvancedCoalRefinerBlockEntity.this.burnTime;
                    case 1 -> AdvancedCoalRefinerBlockEntity.this.fuelTime;
                    case 2 -> AdvancedCoalRefinerBlockEntity.this.cookTime;
                    case 3 -> AdvancedCoalRefinerBlockEntity.this.cookTimeTotal;
                    default -> 0;
                };
            }
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AdvancedCoalRefinerBlockEntity.this.burnTime = value;
                    case 1 -> AdvancedCoalRefinerBlockEntity.this.fuelTime = value;
                    case 2 -> AdvancedCoalRefinerBlockEntity.this.cookTime = value;
                    case 3 -> AdvancedCoalRefinerBlockEntity.this.cookTimeTotal = value;
                }
            }
            @Override
            public int size() {
                return 4;
            }
        };
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public static void tick(World world, AdvancedCoalRefinerBlockEntity blockEntity, BlockPos pos) {
        if (world != null && !world.isClient && blockEntity.isMaster) {
            boolean update = false;
            if (blockEntity.isBurning()) {
                blockEntity.burnTime--;
            } else {
                //if no fuel remaining, decrement progress
                if (blockEntity.cookTime > 0) {
                    blockEntity.cookTime = MathHelper.clamp((blockEntity.cookTime - 2), 0, blockEntity.cookTimeTotal);
                }
            }
            final RefinementRecipe recipe = world.getRecipeManager()
                    .listAllOfType(GARecipeTypes.REFINEMENT_RECIPE_TYPE)
                    .stream()
                    .filter(refinementRecipe -> {
                        if (refinementRecipe.input().test(blockEntity.getStack(blockEntity.firstInputSlot))) {
                            return true;
                        } else {
                            return refinementRecipe.input().test(blockEntity.getStack(blockEntity.secondInputSlot));
                        }
                    }).findFirst()
                    .orElse(null);

            if (recipe != null) {
                blockEntity.cookTimeTotal = recipe.time();
                if (!blockEntity.isBurning()) {
                    blockEntity.burnTime = blockEntity.getItemBurnTime(blockEntity.getStack(blockEntity.fuelSlot));
                    blockEntity.fuelTime = blockEntity.burnTime;
                    if (blockEntity.isBurning()) {
                        update = true;
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
                //TODO: check if can smelt
                if (blockEntity.isBurning()) {
                    ++blockEntity.cookTime;
                    if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                        blockEntity.cookTime = 0;
                        blockEntity.cookTimeTotal = recipe.time();
                        //TODO: Smelt item
                        update = true;
                    }
                }
                if (update) {
                    blockEntity.markDirty();
                }
            } else {
                blockEntity.cookTime = 0;
            }

        }
    }

    public void tryToMakeMultiblock() {
        final int[][] neighbors = checkNeighbors(pos.getX(), pos.getY(), pos.getZ());
        if(neighbors[3][0] == 0 && neighbors[3][1] == 0 && neighbors[3][2] == 0) {
            if (neighbors[2][0] != 0 && neighbors[2][1] != 0 && neighbors[2][2] != 0) {
                if (neighbors[0][1] != pos.getY() || neighbors[1][0] != pos.getX() || neighbors[2][2] != pos.getZ()) {
                    int[][] neighbors2 = checkNeighbors(neighbors[1][0], neighbors[0][1], neighbors[2][2]);
                    if (neighbors2[3][0] == 0 && neighbors2[3][1] == 0 && neighbors2[3][2] == 0) {
                        if (neighbors2[2][0] != 0 && neighbors2[2][1] != 0 && neighbors2[2][2] != 0) {
                            if (neighbors2[0][1] == pos.getY() || neighbors2[1][0] == neighbors[2][0] || neighbors2[2][2] == neighbors[1][2]) {
                                this.tempNeighbors = neighbors;
                                this.tempNeighbors2 = neighbors2;
                                BlockPos tempPos;
                                AdvancedCoalRefinerBlockEntity entity;
                                for (int i = 0; i < 3; i++) {
                                    tempPos = new BlockPos(neighbors[i][0], neighbors[i][1], neighbors[i][2]);
                                    entity = (AdvancedCoalRefinerBlockEntity) this.world.getBlockEntity(tempPos);

                                    if (entity != null) {
                                        entity.setMaster(this);
                                        AdvancedCoalRefinerBlock.setFormed(true, this.world, tempPos);
                                    }

                                    tempPos = new BlockPos(neighbors2[i][0], neighbors2[i][1], neighbors2[i][2]);
                                    entity = (AdvancedCoalRefinerBlockEntity) this.world.getBlockEntity(tempPos);

                                    if (entity != null) {
                                        entity.setMaster(this);
                                        AdvancedCoalRefinerBlock.setFormed(true, this.world, tempPos);
                                    }
                                }
                                this.isMaster = true;
                                this.setMaster(this);
                                AdvancedCoalRefinerBlock.setFormed(true, this.world, pos);

                                tempPos = new BlockPos(neighbors[1][0], neighbors[0][1], neighbors[2][2]);
                                entity = (AdvancedCoalRefinerBlockEntity) this.world.getBlockEntity(tempPos);

                                if (entity != null) {
                                    this.setMaster(this);
                                    AdvancedCoalRefinerBlock.setFormed(true, this.world, tempPos);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void destroyMultiblock() {
        if (this.getMaster() != null) {
            var masterEntity = this.getMaster();
            this.tempNeighbors = masterEntity.tempNeighbors;
            this.tempNeighbors2 = masterEntity.tempNeighbors2;

            masterEntity.isMaster = false;
            masterEntity.setMaster(null);
            AdvancedCoalRefinerBlock.setFormed(false, this.world, masterEntity.getPos());

            BlockPos tempPos;
            AdvancedCoalRefinerBlockEntity entity;

            for (int i = 0; i < 3; i++) {
                tempPos = new BlockPos(tempNeighbors[i][0], tempNeighbors[i][1], tempNeighbors[i][2]);
                entity = (AdvancedCoalRefinerBlockEntity) world.getBlockEntity(tempPos);

                if (entity != null) {
                    entity.setMaster(null);
                    AdvancedCoalRefinerBlock.setFormed(false, world, tempPos);
                }

                tempPos = new BlockPos(tempNeighbors2[i][0], tempNeighbors2[i][1], tempNeighbors2[i][2]);
                entity = (AdvancedCoalRefinerBlockEntity) world.getBlockEntity(tempPos);

                if (entity != null) {
                    entity.setMaster(null);
                    AdvancedCoalRefinerBlock.setFormed(false, world, tempPos);
                }
            }

            tempPos = new BlockPos(tempNeighbors[1][0], tempNeighbors[0][1], tempNeighbors[2][2]);
            entity = (AdvancedCoalRefinerBlockEntity) world.getBlockEntity(tempPos);

            if (entity != null) {
                entity.setMaster(null);
                AdvancedCoalRefinerBlock.setFormed(false, world, tempPos);
            }
        }
    }

    private int[][] checkNeighbors(int x, int y, int z) {
        int[][] neighbors = new int[6][3];
        int i = 0;
        Block block = GAObjects.ADVANCED_COAL_REFINER;
        if(this.world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == block) {
            neighbors[i][0] = x;
            neighbors[i][1] = y + 1;
            neighbors[i][2] = z;
            i++;
        }
        if(this.world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == block) {
            neighbors[i][0] = x;
            neighbors[i][1] = y - 1;
            neighbors[i][2] = z;
            i++;
        }
        if(this.world.getBlockState(new BlockPos(x + 1, y, z)).getBlock() == block) {
            neighbors[i][0] = x + 1;
            neighbors[i][1] = y;
            neighbors[i][2] = z;
            i++;
        }
        if(this.world.getBlockState(new BlockPos(x - 1, y, z)).getBlock() == block) {
            neighbors[i][0] = x - 1;
            neighbors[i][1] = y;
            neighbors[i][2] = z;
            i++;
        }
        if(this.world.getBlockState(new BlockPos(x, y, z + 1)).getBlock() == block) {
            neighbors[i][0] = x;
            neighbors[i][1] = y;
            neighbors[i][2] = z + 1;
            i++;
        }
        if(this.world.getBlockState(new BlockPos(x, y, z - 1)).getBlock() == block) {
            neighbors[i][0] = x;
            neighbors[i][1] = y;
            neighbors[i][2] = z - 1;
        }
        return neighbors;
    }

    //get master entity
    public AdvancedCoalRefinerBlockEntity getMaster() {
        if (this.isValid && this.masterPos != null) {
            return (AdvancedCoalRefinerBlockEntity) this.world.getBlockEntity(this.masterPos);
        }
        return null;
    }

    //set master entity
    public void setMaster(AdvancedCoalRefinerBlockEntity master) {
        if (master != null) {
            this.isValid = true;
            this.masterPos = new BlockPos(master.getPos().getX(), master.getPos().getY(), master.getPos().getZ());
        } else {
            this.isValid = false;
            this.masterPos = null;
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.isMaster = nbt.getBoolean("isMaster");
        this.isValid = nbt.getBoolean("isValid");
        if (this.isValid) {
            this.masterPos = new BlockPos(nbt.getInt("masterX"), nbt.getInt("masterY"), nbt.getInt("masterZ"));
        } else {
            this.masterPos = null;
        }
        if (this.isMaster) {
            //TempNeighbors1
            NbtList tempList = nbt.getList("tempNeighbors", NbtElement.COMPOUND_TYPE);
            int[][] tempNeighborData = new int[6][3];
            for (int i = 0; i < tempList.size(); i++) {
                NbtCompound compound = tempList.getCompound(i);
                tempNeighborData[i] = compound.getIntArray("temp");
            }
            this.tempNeighbors = tempNeighborData;

            //TempNeighbors2
            tempList = nbt.getList("tempNeighbors2", NbtElement.COMPOUND_TYPE);
            int[][] tempNeighbor2Data = new int[6][3];
            for (int i = 0; i < tempList.size(); i++) {
                NbtCompound compound = tempList.getCompound(i);
                tempNeighbor2Data[i] = compound.getIntArray("temp");
            }
            this.tempNeighbors2 = tempNeighbor2Data;
        }
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("isMaster", this.isMaster);
        nbt.putBoolean("isValid", this.isValid);
        if (this.isValid && this.masterPos != null) {
            nbt.putInt("masterX", this.masterPos.getX());
            nbt.putInt("masterY", this.masterPos.getY());
            nbt.putInt("masterZ", this.masterPos.getZ());
        }
        if (this.isMaster) {
            if (this.tempNeighbors != null) {
                NbtList tempNeighborData = new NbtList();
                for (int[] tneighbor : tempNeighbors) {
                    NbtCompound compound = new NbtCompound();
                    compound.putIntArray("temp", tneighbor);
                    tempNeighborData.add(compound);
                }
                nbt.put("tempNeighbors", tempNeighborData);
            }
            if (this.tempNeighbors2 != null) {
                NbtList tempNeighbor2Data = new NbtList();
                for (int[] tneighbor2 : this.tempNeighbors2) {
                    NbtCompound compound = new NbtCompound();
                    compound.putIntArray("temp", tneighbor2);
                    tempNeighbor2Data.add(compound);
                }
                nbt.put("tempNeighbors2", tempNeighbor2Data);
            }
        }
    }

    @Override
    public ItemStack getStack(int slot) {
        if (this.isMultiblockFormed()) {
            return this.getMaster().getStack(slot);
        } else {
            return super.getStack(slot);
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (this.isMultiblockFormed()) {
            this.getMaster().setStack(slot, stack);
        } else {
            super.setStack(slot, stack);
        }
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (this.isMultiblockFormed()) {
            return this.getMaster().removeStack(slot, amount);
        } else {
            return super.removeStack(slot, amount);
        }
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (this.isMultiblockFormed()) {
            return this.getMaster().removeStack(slot);
        }
        return super.removeStack(slot);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[] {0, 1, 2, 3, 4};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (this.isValid) {
            if (this.getMaster() == world.getBlockEntity(pos)) {
                if (slot == this.firstInputSlot || slot == this.secondInputSlot) {
                    return stack.isIn(ItemTags.COALS) && stack.getItem() != Items.CHARCOAL;
                } else if (slot == this.fuelSlot) {
                    return this.getItemBurnTime(stack) > 0;
                }
            } else if (this.getMaster() != null) {
                return this.getMaster().canInsert(slot, stack, dir);
            }
        }
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (this.isValid) {
            if (this.getMaster() == world.getBlockEntity(pos)) {
                return slot == this.firstOutputSlot || slot == this.secondOutputSlot;
            } else if (this.getMaster() != null) {
                return this.getMaster().canExtract(slot, stack, dir);
            }
        }
        return false;
    }

    public boolean isMultiblockFormed() {
        return this.isValid && this.getMaster() != null && this.getMaster() != world.getBlockEntity(this.pos);
    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AdvancedCoalRefinerScreenHandler(syncId, inv, this, this.delegate);
    }
}
