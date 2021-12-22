package daniking.geoactivity.common.block;

import daniking.geoactivity.common.block.entity.CoalRefinerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CoalRefinerBlock extends GAMachineBlock {

    public static final DirectionProperty FACING;
    public static final BooleanProperty LIT;

    static {
        FACING =  HorizontalFacingBlock.FACING;
        LIT =  Properties.LIT;
    }

    public CoalRefinerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(LIT, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoalRefinerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> {
            if (blockEntity instanceof CoalRefinerBlockEntity) {
                CoalRefinerBlockEntity.tick(tickerWorld, pos, tickerState, (CoalRefinerBlockEntity) blockEntity);
            }
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            final double d = (double)pos.getX() + 0.5D;
            final double e = pos.getY();
            final double f = (double)pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            final Direction direction = state.get(FACING);
            final Direction.Axis axis = direction.getAxis();
            final double g = 0.52D;
            final double h = random.nextDouble() * 0.6D - 0.3D;
            final double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * g : h;
            final double j = random.nextDouble() * 6.0D / 16.0D;
            final double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * g : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
        }
    }

}
