/*
 *     This file is part of FabricToys.
 *
 *     FabricToys is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     FabricToys is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with FabricToys.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.blayyke.fabrictoys.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import io.github.blayyke.fabrictoys.FabricToys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ModConfig {
    private final File file;
    private final Jankson jankson;

    public ModConfig(File configFile) {
        this.file = configFile;
        file.getParentFile().mkdirs();

        this.jankson = Jankson.builder().build();
    }

    public ConfigVals read() throws IOException {
        try {
            JsonObject configJson = jankson.load(file);
            ConfigVals configVals = jankson.fromJson(configJson, ConfigVals.class);
            FabricToys.LOGGER.info("Loaded config!");
            return configVals;
        } catch (SyntaxError syntaxError) {
            throw new RuntimeException(syntaxError);
        }
    }

    public void saveDefaultConfig() {
        String result = jankson
                .toJson(new ConfigVals())
                .toJson(true, true, 0);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file, false);
            out.write(result.getBytes());
            out.flush();
            out.close();

            FabricToys.LOGGER.info("Saved default config!");
        } catch (IOException e) {
            FabricToys.LOGGER.warn("Failed to save config defaults!");
            e.printStackTrace();
        }
    }
}