package com.handler.bot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.handler.bot.constants.Constant.*;

@Service
public class AuthService {
    private String accessToken;

    public String authenticate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", CLIENT_ID);
        body.put("client_secret", CLIENT_SECRET);
        body.put("grant_type", "client_credentials");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(AUTH_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            accessToken = response.getBody().get("access_token").toString();
            return accessToken;
        } else {
            throw new RuntimeException("Failed to authenticate: " + response.getBody());
        }
    }

    public String getAccessToken() {
        return accessToken == null ? authenticate() : accessToken;
    }
}
