package daniking.geoactivity.common.registry;

import net.fabricmc.fabric.api.registry.FuelRegistry;

public final class GAFuels {

    public static void init() {
        final FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(GAObjects.LIGNITE_COAL, 600);
        registry.add(GAObjects.LIGNITE_COAL_BLOCK, 6000);
        registry.add(GAObjects.BITUMINOUS_COAL, 2000);
        registry.add(GAObjects.BITUMINOUS_COAL_BLOCK, 20000);
        registry.add(GAObjects.ANTHRACITE_COAL, 3200);
        registry.add(GAObjects.ANTHRACITE_COAL_BLOCK, 32000);
    }
}
