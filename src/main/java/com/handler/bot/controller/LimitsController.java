package com.handler.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handler.bot.model.LimitsRes;
import com.handler.bot.service.LimitsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/limits")
public class LimitsController {
    private final LimitsService limitsService;
    private static final Logger logger = LoggerFactory.getLogger(LimitsController.class);
    Map<String, Object> requestBody = Map.of(
            "seg", "CASH",
            "exch", "NSE",
            "prod", "ALL"
    );
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LimitsController(LimitsService limitsService) {
        this.limitsService = limitsService;
    }

    @SuppressWarnings("unchecked")
    @PostMapping
    public LimitsRes getLimits() {
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            LimitsRes limits = limitsService.fetchLimits(objectMapper.readValue(jsonRequestBody, Map.class));
            logger.info("Limits Fetched : {}", limits);
            return limits;
        } catch (Exception e) {
            logger.error("Error converting map to JSON", e);
            return null;
        }
    }
}
