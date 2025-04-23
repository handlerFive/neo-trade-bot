package com.handler.bot.controller;

import com.handler.bot.model.QuoteRes;
import com.handler.bot.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/get")
    public ResponseEntity<Mono<QuoteRes>> getQuotes(@RequestParam("symbols") String symbols) {
        return ResponseEntity.ok(quoteService.getQuotes(symbols));
    }
} 