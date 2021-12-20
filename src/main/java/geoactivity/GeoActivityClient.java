package geoactivity;

import geoactivity.client.gui.screen.*;
import geoactivity.common.event.handler.ItemTooltipHandler;
import geoactivity.common.registry.GAObjects;
import geoactivity.common.registry.GAScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class GeoActivityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), GAObjects.HARDENED_GLASS, GAObjects.GRAPHITE_BASE);
        ItemTooltipCallback.EVENT.register(new ItemTooltipHandler());
        ScreenRegistry.register(GAScreenHandlerTypes.COAL_REFINER, CoalRefinerScreen::new);
        ScreenRegistry.register(GAScreenHandlerTypes.REINFORCED_MINER, ReinforcedMinerScreen::new);
        ScreenRegistry.register(GAScreenHandlerTypes.AUTO_STONE_BUILDER, AutoStoneBuilderScreen::new);
        ScreenRegistry.register(GAScreenHandlerTypes.CRAFTING_MACHINE, CraftingMachineScreen::new);
        ScreenRegistry.register(GAScreenHandlerTypes.BASIC_UPGRADE, BasicUpgradeScreen::new);
    }
}
