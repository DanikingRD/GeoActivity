package geoactivity.common;

import geoactivity.GeoActivity;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;


/*
 * Config file class
 */
@Config(name = GeoActivity.MODID)
public class GAConfig implements ConfigData {
    /*
     * Lignite Ore
     */
    public int ligniteVeinSize = 3;
    public int ligniteMinHeight = 46;
    public int ligniteMaxHeight = 64;
    public int ligniteVeinsPerChunk = 14;
    /**
     * Bituminous Ore
     */
    public int bituminousVeinSize = 3;
    public int bituminousMinHeight = 34;
    public int bituminousMaxHeight = 48;
    public int bituminousVeinsPerkChunk = 14;    
    /**
     *  Anthracite Ore
     */
    public int anthraciteVeinSize = 3;
    public int anthraciteMinHeight = 18;
    public int anthraciteMaxHeight = 38;
    public int anthraciteVeinsPerkChunk = 14;    
}
