package daniking.geoactivity.common.registry;

import daniking.geoactivity.client.gui.screen.handler.*;
import daniking.geoactivity.GeoActivity;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public final class GAScreenHandlerTypes {

    public static final ScreenHandlerType<CoalRefinerScreenHandler> COAL_REFINER = simple("coal_refiner", CoalRefinerScreenHandler::new);
    public static final ScreenHandlerType<CraftingMachineScreenHandler> CRAFTING_MACHINE = simple("crafting_machine", CraftingMachineScreenHandler::new);
    public static final ScreenHandlerType<ReinforcedMinerScreenHandler> REINFORCED_MINER = simple("reinforced_miner", ReinforcedMinerScreenHandler::new);
    public static final ScreenHandlerType<AutoStoneBuilderScreenHandler> AUTO_STONE_BUILDER = simple("auto_stone_builder", AutoStoneBuilderScreenHandler::new);
    public static final ScreenHandlerType<BasicUpgradeScreenHandler> BASIC_UPGRADE = simple("basic_upgrade", BasicUpgradeScreenHandler::new);

    public static <T extends ScreenHandler> ScreenHandlerType<T> simple(final String name, final ScreenHandlerRegistry.SimpleClientHandlerFactory<T> factory) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(GeoActivity.MODID, name), factory);
    }
}
