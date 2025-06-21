package com.handler.bot.controller;

import com.handler.bot.model.HoldingsResponse;
import com.handler.bot.service.HoldingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/holdings")
public class HoldingsController {

    private final HoldingsService holdingsService;
    private static final Logger logger = LoggerFactory.getLogger(HoldingsController.class);

    @Autowired
    public HoldingsController(HoldingsService holdingsService) {
        this.holdingsService = holdingsService;
    }

    @GetMapping
    public HoldingsResponse getHoldings() {
        HoldingsResponse response = holdingsService.fetchHoldings();
        logger.info("Fetched holdings: {}", response);
        return response;
    }
}
