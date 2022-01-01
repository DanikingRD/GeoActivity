package daniking.geoactivity.common.block;

import daniking.geoactivity.api.block.entity.IMachineExperienceHandler;
import daniking.geoactivity.common.block.entity.AdvancedCoalRefinerBlockEntity;
import daniking.geoactivity.common.block.entity.GAMachineBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AdvancedCoalRefinerBlock extends GAMachineBlock{

    public static final BooleanProperty FORMED = BooleanProperty.of("formed");

    public AdvancedCoalRefinerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FORMED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && !player.isSneaking()) {
            final BlockEntity entity = world.getBlockEntity(pos);
            if (entity != null) {
                if (entity instanceof AdvancedCoalRefinerBlockEntity machineEntity) {
                    if (machineEntity.getMaster() != null) {
                        player.openHandledScreen(machineEntity.getMaster());
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    public static void setFormed(boolean formed, World world, BlockPos pos) {
       final BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(FORMED, formed), 2);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if (!world.isClient) {
            final BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof AdvancedCoalRefinerBlockEntity machine) {
                machine.tryToMakeMultiblock();
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (world.getBlockEntity(pos) instanceof AdvancedCoalRefinerBlockEntity) {
                world.updateComparators(pos, this);
            }
            world.removeBlockEntity(pos);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AdvancedCoalRefinerBlockEntity machine) {
            if (machine.getMaster() != null) {
                ItemScatterer.spawn(world, pos, machine.getMaster());
                machine.destroyMultiblock();
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        var entity = world.getBlockEntity(pos);
        if (entity != null) {
            var machine = (AdvancedCoalRefinerBlockEntity) entity;
            if (machine.getMaster() != null) {
                return ScreenHandler.calculateComparatorOutput((BlockEntity) machine.getMaster());
            }
        }
        return 0;
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AdvancedCoalRefinerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> AdvancedCoalRefinerBlockEntity.tick(tickerWorld, (AdvancedCoalRefinerBlockEntity) blockEntity, pos);
    }
}
