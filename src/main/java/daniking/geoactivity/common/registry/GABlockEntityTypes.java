package daniking.geoactivity.common.registry;

import daniking.geoactivity.GeoActivity;
import daniking.geoactivity.common.block.entity.AdvancedCoalRefinerBlockEntity;
import daniking.geoactivity.common.block.entity.CoalRefinerBlockEntity;
import daniking.geoactivity.common.block.entity.CraftingMachineBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class GABlockEntityTypes {

    public static final BlockEntityType<CoalRefinerBlockEntity> COAL_REFINER = FabricBlockEntityTypeBuilder.create(CoalRefinerBlockEntity::new, GAObjects.COAL_REFINER).build();
    public static final BlockEntityType<CraftingMachineBlockEntity> CRAFTING_MACHINE = FabricBlockEntityTypeBuilder.create(CraftingMachineBlockEntity::new, GAObjects.CRAFTING_MACHINE).build();
    public static final BlockEntityType<AdvancedCoalRefinerBlockEntity> ADVANCED_COAL_REFINER = FabricBlockEntityTypeBuilder.create(AdvancedCoalRefinerBlockEntity::new, GAObjects.ADVANCED_COAL_REFINER).build();

    public static void init() {
        register("coal_refiner", COAL_REFINER);
        register("crafting_machine", CRAFTING_MACHINE);
        register("advanced_coal_refiner", ADVANCED_COAL_REFINER);
    }

    public static  void register(String id, BlockEntityType<? extends BlockEntity> blockEntity) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(GeoActivity.MODID, id), blockEntity);
    }

}
