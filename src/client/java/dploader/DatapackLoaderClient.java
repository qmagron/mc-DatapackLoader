package dploader;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class DatapackLoaderClient implements ClientModInitializer {
	public static final String MOD_ID = "dploader";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Path DATAPACK_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		if (!Files.exists(DATAPACK_PATH)) {
			LOGGER.info("Creating datapack directory");

			try {
				Files.createDirectory(DATAPACK_PATH);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
	}
}
