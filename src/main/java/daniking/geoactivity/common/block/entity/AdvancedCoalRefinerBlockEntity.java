package daniking.geoactivity.common.block.entity;

import daniking.geoactivity.client.gui.screen.handler.AdvancedCoalRefinerScreenHandler;
import daniking.geoactivity.common.block.AdvancedCoalRefinerBlock;
import daniking.geoactivity.common.registry.GABlockEntityTypes;
import daniking.geoactivity.common.registry.GAObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AdvancedCoalRefinerBlockEntity extends GAMachineBlockEntity{

    private int[][] tempNeighbors;
    private int[][] tempNeighbors2;

    private BlockPos masterPos = null;
    private boolean isValid = false;
    private boolean isMaster = false;

    public AdvancedCoalRefinerBlockEntity(BlockPos pos, BlockState state) {
        super(GABlockEntityTypes.ADVANCED_COAL_REFINER, 7, pos, state);
    }

    public static void tick(World world, AdvancedCoalRefinerBlockEntity blockEntity, BlockPos pos) {
        if (!world.isClient && blockEntity.isMaster) {

        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AdvancedCoalRefinerScreenHandler(syncId, inv, this);
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
        if (this.world != null && this.getMaster() != null) {
            var master = this.getMaster();
            this.tempNeighbors = master.tempNeighbors;
            this.tempNeighbors2 = master.tempNeighbors2;

            master.isMaster = false;
            master.setMaster(null);
            AdvancedCoalRefinerBlock.setFormed(false, this.world, master.getPos());
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


    public boolean isMultiblockFormed() {
        return this.isValid && this.getMaster() != null && this.getMaster() != world.getBlockEntity(this.pos);
    }
}
