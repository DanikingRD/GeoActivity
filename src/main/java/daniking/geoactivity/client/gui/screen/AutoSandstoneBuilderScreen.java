package daniking.geoactivity.client.gui.screen;

import daniking.geoactivity.client.gui.screen.handler.AutoSandstoneScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class AutoSandstoneBuilderScreen extends BlockBuilderScreen<AutoSandstoneScreenHandler> {
    public AutoSandstoneBuilderScreen(AutoSandstoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
