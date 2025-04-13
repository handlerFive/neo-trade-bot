package com.handler.bot.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;

public class JwtDecoder {

    public static String extractSubFromJwt(String jwtToken) throws Exception {
        String[] parts = jwtToken.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payloadMap = objectMapper.readValue(payloadJson, new TypeReference<>() {
        });
        return (String) payloadMap.get("sub");
    }
}
