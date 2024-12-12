package com.example.ticketManager.util.handler;

import com.example.ticketing.util.interfaces.IEventLogger;

public class LoginHandler {
    boolean isBackend = false;
    IEventLogger logger;

    public LoginHandler(IEventLogger logger) {
        this.logger = logger;
    }

    public LoginHandler(IEventLogger logger, boolean isBackend) {
        this(logger);
        this.isBackend = isBackend;
    }


}
