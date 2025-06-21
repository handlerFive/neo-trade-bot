package com.handler.bot.controller;


import com.handler.bot.model.PositionBookResponse;
import com.handler.bot.service.PositionBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/positions")
public class PositionBookController {

    private final PositionBookService positionBookService;
    private static final Logger logger = LoggerFactory.getLogger(PositionBookController.class);

    @Autowired
    public PositionBookController(PositionBookService positionBookService) {
        this.positionBookService = positionBookService;
    }

    @GetMapping
    public PositionBookResponse getPositions() {
        PositionBookResponse response = positionBookService.fetchPositionBook();
        logger.info("Position Book Response: {}", response);
        return response;
    }
}
