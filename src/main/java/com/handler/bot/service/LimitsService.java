package com.handler.bot.service;

import com.handler.bot.component.SessionTokenStorage;
import com.handler.bot.model.LimitsRes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.handler.bot.constants.Constant.API_SUB_KEY;
import static com.handler.bot.constants.Constant.LIMITS_URL;

@Service
public class LimitsService {

    private final WebClient webClient;
    private final SessionTokenStorage sessionTokenStorage;
    private final AuthService authService;

    public LimitsService(WebClient webClient, SessionTokenStorage sessionTokenStorage, AuthService authService) {
        this.webClient = webClient;
        this.sessionTokenStorage = sessionTokenStorage;
        this.authService = authService;
    }

    public LimitsRes fetchLimits(Map<String, Object> requestBody) {
        return webClient.post()
                .uri(LIMITS_URL)
                .header("Sid", sessionTokenStorage.getSid())
                .header("Auth", sessionTokenStorage.getSessionToken())
                .header("neo-fin-key", API_SUB_KEY)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + authService.getAccessToken())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(LimitsRes.class)
                .block();
    }
}
