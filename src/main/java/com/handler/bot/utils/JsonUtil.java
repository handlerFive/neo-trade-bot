package com.handler.bot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing object: " + object.getClass().getSimpleName(), e);
        }
    }
}

