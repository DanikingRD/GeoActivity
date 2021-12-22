package daniking.geoactivity.common.util;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GAUtil {


    /**
     * Calculates the amount of experience to drop, and spawns it in the world.
     * @param world ServerWorld
     * @param playerPos Vec3d of player.
     * @param multiplier Multiplier is the amount of items where crafted.
     * @param experience Experience from recipe.
     */
    public static void calculateAndDropExperience(final ServerWorld world, final Vec3d playerPos, final int multiplier, final float experience) {
        int round = MathHelper.floor((float) multiplier * experience);
        final float fraction = MathHelper.fractionalPart((float)multiplier * experience);
        if (fraction > 0.0F && Math.random() < (double)fraction) {
            ++round;
        }
        ExperienceOrbEntity.spawn(world, playerPos, round);
    }
}
