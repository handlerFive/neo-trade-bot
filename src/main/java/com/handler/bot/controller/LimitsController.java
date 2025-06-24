package com.handler.bot.controller;

import com.handler.bot.model.LimitsRes;
import com.handler.bot.service.LimitsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/limits")
public class LimitsController {
    private final LimitsService limitsService;
    private static final Logger logger = LoggerFactory.getLogger(LimitsController.class);

    public LimitsController(LimitsService limitsService) {
        this.limitsService = limitsService;
    }

    @PostMapping
    public LimitsRes getLimits() {
        LimitsRes limits = limitsService.fetchLimits();
        logger.info("Limits Fetched");
        return limits;
    }
}
