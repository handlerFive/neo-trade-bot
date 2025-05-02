package com.handler.bot.controller;

import com.handler.bot.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wso2.client.model.Quotes_API.InlineResponse200;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<InlineResponse200>> getQuotes(@RequestParam("symbols") String symbols) {
        List<InlineResponse200> inlineResponse200List = quoteService.getQuotes(symbols);
        return ResponseEntity.ok(inlineResponse200List);
    }
} 