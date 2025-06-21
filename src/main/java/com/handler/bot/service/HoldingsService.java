package com.handler.bot.service;

import com.handler.bot.component.SessionTokenStorage;
import com.handler.bot.model.HoldingsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.handler.bot.constants.Constant.API_SUB_KEY;
import static com.handler.bot.constants.Constant.HOLDINGS_URL;

@Service
public class HoldingsService {

    private final WebClient webClient;
    private final SessionTokenStorage sessionTokenStorage;
    private final AuthService authService;

    public HoldingsService(WebClient webClient, SessionTokenStorage sessionTokenStorage, AuthService authService) {
        this.webClient = webClient;
        this.sessionTokenStorage = sessionTokenStorage;
        this.authService = authService;
    }

    public HoldingsResponse fetchHoldings() {
        return webClient.get()
                .uri(HOLDINGS_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("Sid", sessionTokenStorage.getSid())
                .header("Auth", sessionTokenStorage.getSessionToken())
                .header("neo-fin-key", API_SUB_KEY)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("Authorization", "Bearer " + authService.getAccessToken())
                .retrieve()
                .bodyToMono(HoldingsResponse.class)
                .block();
    }
}
