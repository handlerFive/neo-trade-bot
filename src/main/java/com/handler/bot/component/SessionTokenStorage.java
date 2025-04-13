package com.handler.bot.component;

import org.springframework.stereotype.Component;

@Component
public class SessionTokenStorage {

    private String sessionToken;

    public synchronized void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public synchronized String getSessionToken() {
        return sessionToken;
    }

    public synchronized void clear() {
        this.sessionToken = null;
    }
}