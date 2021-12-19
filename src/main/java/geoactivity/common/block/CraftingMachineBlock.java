package geoactivity.common.block;

import geoactivity.common.block.entity.CraftingMachineBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CraftingMachineBlock extends GABlockContainer {

    public CraftingMachineBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CraftingMachineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return ((tickerWorld, pos, tickerState, blockEntity) -> CraftingMachineBlockEntity.tick(tickerWorld, pos, tickerState, (CraftingMachineBlockEntity) blockEntity));
    }
}
