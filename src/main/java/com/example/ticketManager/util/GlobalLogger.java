package com.example.ticketManager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalLogger {
    private static final Logger logger = LoggerFactory.getLogger(GlobalLogger.class);

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message, Throwable t) {
        logger.error(message, t);
    }

    public static void logDebug(String message) {
        logger.debug(message);
    }
}
