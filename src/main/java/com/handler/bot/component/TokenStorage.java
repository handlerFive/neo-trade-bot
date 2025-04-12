package com.handler.bot.component;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenStorage {

    private String accessToken;
    private Instant expiryTime;

    public synchronized void updateToken(String token, long expiresInSeconds) {
        this.accessToken = token;
        this.expiryTime = Instant.now().plusSeconds(expiresInSeconds - 30); // Refresh 30 sec before expiry
    }

    public synchronized String  getAccessToken() {
        return accessToken;
    }

    public synchronized boolean isTokenExpired() {
        return expiryTime == null || Instant.now().isAfter(expiryTime);
    }
}
