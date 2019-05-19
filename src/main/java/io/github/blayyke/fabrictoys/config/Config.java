package io.github.blayyke.fabrictoys.config;

import io.github.blayyke.fabrictoys.FabricToys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Config {
    private final File file;

    @ConfigKey
    public boolean enableCampfireTweak;

    public Config(File configFile) {
        this.file = configFile;
    }

    public void read() throws IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();

        loadValues();

        FabricToys.LOGGER.info("Read values from config");
    }

    private void loadValues() throws IOException {
        FileInputStream stream = new FileInputStream(file);
        try {
            Properties properties = new Properties();
            properties.load(stream);
            this.enableCampfireTweak = Boolean.parseBoolean(properties.getProperty("enableCampfireTweak", "true"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
    }

    public void write() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("enableCampfireTweak", String.valueOf(enableCampfireTweak));

        FileOutputStream stream = new FileOutputStream(file);
        try {
            properties.store(stream, "Properties");
            loadValues();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }

        FabricToys.LOGGER.info("Saved config to file");
    }

    public ConfigUpdateResult trySetValue(String key, Object value) {
        try {
            Field declaredField = getClass().getDeclaredField(key);
            if (declaredField.isAnnotationPresent(ConfigKey.class)) {
                Object oldValue = declaredField.get(this);
                declaredField.set(this, value);
                write();
                ConfigUpdateResult result = ConfigUpdateResult.success("Changed config option '" + key + "' from '" + oldValue + "' to '" + value + "'.");
                FabricToys.LOGGER.info(result.getMessage());
                return result;
            }
            return ConfigUpdateResult.failure("key not found");
        } catch (NoSuchFieldException e) {
            return ConfigUpdateResult.failure("key not found");
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}