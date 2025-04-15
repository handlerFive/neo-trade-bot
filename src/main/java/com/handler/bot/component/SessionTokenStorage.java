package com.handler.bot.component;

import org.springframework.stereotype.Component;

@Component
public class SessionTokenStorage {

    private String sessionToken;
    private String sid;

    public synchronized void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public synchronized String getSessionToken() {
        return sessionToken;
    }

    public synchronized void clear() {
        this.sessionToken = null;
    }

    public synchronized String getSid() {
        return sid;
    }

    public synchronized void setSid(String sid) {
        this.sid = sid;
    }
}