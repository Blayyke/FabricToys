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