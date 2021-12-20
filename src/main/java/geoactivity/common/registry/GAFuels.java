package geoactivity.common.registry;

import net.fabricmc.fabric.api.registry.FuelRegistry;

public class GAFuels {

    public static void init() {
        final FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(GAObjects.LIGNITE_COAL, 400);
        registry.add(GAObjects.BITUMINOUS_COAL, 1200);
        registry.add(GAObjects.ANTHRACITE_COAL, 3200);
    }
}
