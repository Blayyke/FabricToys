package io.github.blayyke.fabrictoys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final File file;

    public boolean enableCampfireTweak;

    public Config(File configFile) {
        this.file = configFile;
    }

    public void read() throws IOException {
        Properties properties = new Properties();

        file.getParentFile().mkdirs();
        file.createNewFile();

        FileInputStream stream = new FileInputStream(file);
        try {
            properties.load(stream);

            this.enableCampfireTweak = Boolean.parseBoolean(properties.getProperty("enableCampfireTweak", "true"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }

        FabricToys.LOGGER.info("Read values from config");
    }

    public void write() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("enableCampfireTweak", String.valueOf(enableCampfireTweak));

        FileOutputStream stream = new FileOutputStream(file);
        try {
            properties.store(stream, "Properties");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }

        FabricToys.LOGGER.info("Saved config to file");
    }
}