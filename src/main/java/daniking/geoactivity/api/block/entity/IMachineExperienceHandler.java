package daniking.geoactivity.api.block.entity;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@FunctionalInterface
public interface IMachineExperienceHandler {

     void dropExperience(final ServerWorld world, final Vec3d playerPos);

}
