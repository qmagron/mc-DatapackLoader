package dploader.mixin.client;

import java.nio.file.Path;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dploader.DatapackLoaderClient;
import dploader.FileUtils;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
	@Unique
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateWorldScreenMixin.class);

	@Shadow
	@Nullable
	private Path dataPackTempDir;

	// <=1.21.1 - getDataPackTempDir
	//  >1.21.1 - getOrCreateDataPackTempDir
	@Inject(method = "method_29693", at = @At("RETURN"))
	private void injectDatapacks(CallbackInfoReturnable<Path> info) {
		Path globalDatapackDir = DatapackLoaderClient.DATAPACK_PATH;

		LOGGER.info("Copying datapacks to " + dataPackTempDir);

		try {
			FileUtils.syncDirs(globalDatapackDir, dataPackTempDir);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
