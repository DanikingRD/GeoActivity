package geoactivity.common.block;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;


/**
 * Used for Ore blocks
 */
public class GAOreBlock extends OreBlock {
    
    /**
     * @param settings Block settings
     */
    public GAOreBlock(final Settings settings) {
        super(settings);
    }

    /**
     * Drops experience when the block is mined
     * @param settings Block settings
     * @param experienceDropped Experience dropped
     */
    public GAOreBlock(final Settings settings, final UniformIntProvider experienceDropped) {
        super(settings, experienceDropped);
    }    

}
