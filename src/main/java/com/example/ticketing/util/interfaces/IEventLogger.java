package com.example.ticketing.util.interfaces;

import com.example.ticketing.model.User;

/**
 * IEventLogger is an interface for logging events.
 */
public interface IEventLogger {

    /**
     * Logs a message.
     *
     * @param message the message to log
     */
    void log(String message);

    /**
     * @param message
     * @param user
     */
    void log(String message, User user);

    /**
     * Starts the logger.
     */
    void stop();

    void error(String message);
}
