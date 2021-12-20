package geoactivity.common.registry;

import geoactivity.common.event.handler.AttackBlockHandler;
import geoactivity.common.event.handler.UseItemHandler;

public final class GAEvents {

    public static void init() {
        AttackBlockHandler.EVENT.register(new AttackBlockHandler());
        UseItemHandler.EVENT.register(new UseItemHandler());
    }
}
