package geoactivity.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class SmelterBlockEntity extends GABlockEntityBase {

    public SmelterBlockEntity(BlockEntityType<?> type, int size, BlockPos pos, BlockState state) {
        super(type, size, pos, state);
    }

    protected int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        return (AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(stack.getItem(), 0) / 4);
    }

    /**
     * Called onBreak() from Block and onCrafted() from output slot.
     */
    public void dropExperience(final ServerWorld world, final Vec3d playerPos) {
        calculateAndDropExperience(world, playerPos, 0, 0.0F);
    }

    /**
     * Calculates the amount of experience to drop, and spawns it in the world.
     * @param world ServerWorld
     * @param playerPos Vec3d of player.
     * @param multiplier Multiplier is the amount of items where crafted.
     * @param experience Experience from recipe.
     */
    protected static void calculateAndDropExperience(final ServerWorld world, final Vec3d playerPos, final int multiplier, final float experience) {
        int round = MathHelper.floor((float) multiplier * experience);
        final float fraction = MathHelper.fractionalPart((float)multiplier * experience);
        if (fraction > 0.0F && Math.random() < (double)fraction) {
            ++round;
        }
        ExperienceOrbEntity.spawn(world, playerPos, round);
    }
}
