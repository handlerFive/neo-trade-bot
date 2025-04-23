package com.handler.bot.service;

import com.handler.bot.model.QuoteRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.handler.bot.constants.Constant.QUOTES_URL;

@Service
public class QuoteService {
    private final WebClient webClient;
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    public QuoteService(WebClient webClient, AuthService authService) {
        this.webClient = webClient;
        this.authService = authService;
    }

    public Mono<QuoteRes> getQuotes(String symbols) {
        logger.info("Encoded symbols: {}", symbols);
        String encodedSymbols = symbols.replace("|", "%7C")
                .replace(",", "%2C");
        return webClient.get()
                .uri(QUOTES_URL + encodedSymbols + "/all")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.generateBasicToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
} 