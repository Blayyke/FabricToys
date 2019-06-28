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

package io.github.blayyke.fabrictoys;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrefixedLogger {
    private final Logger logger;

    public PrefixedLogger(String name) {
        this.logger = LogManager.getLogger(name);
    }

    public void info(String s) {
        logger.info(prefix(s));
    }

    public void error(String s) {
        logger.error(prefix(s));
    }

    public void warn(String s) {
        logger.warn(prefix(s));
    }

    public void debug(String s) {
        logger.debug(prefix(s));
    }

    private String prefix(String s) {
        return "[" + this.logger.getName() + "] " + s;
    }
}