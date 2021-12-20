package geoactivity.common;

import geoactivity.GeoActivity;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;


@Config(name = GeoActivity.MODID)
public class GAConfig implements ConfigData {

    public boolean generateLignite = true;
    public boolean generateBituminous = true;
    public boolean generateAnthracite = true;

}
