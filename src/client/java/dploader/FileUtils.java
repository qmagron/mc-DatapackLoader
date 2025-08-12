package dploader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileUtils {
  /**
   * Make dstDr being a copy of srcDir.
   */
  public static void syncDirs(Path srcDir, Path dstDir) throws IOException {
    String[] datapacks = srcDir.toFile().list();

    // Remove obsolete files
    Files.walk(dstDir)
      .forEach(file -> {
        String filename = file.getFileName().toString();

        if (!Arrays.stream(datapacks).anyMatch(filename::equals)) {
          file.toFile().delete();
        }
      });

    org.apache.commons.io.FileUtils.copyDirectory(srcDir.toFile(), dstDir.toFile());
  }
}
