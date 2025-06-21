package com.handler.bot.service;

import com.handler.bot.component.SessionTokenStorage;
import com.handler.bot.model.PositionBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.handler.bot.constants.Constant.API_SUB_KEY;
import static com.handler.bot.constants.Constant.POSITION_URL;

@Service
@Slf4j
public class PositionBookService {

    private final AuthService authService;
    private final WebClient webClient;
    private final SessionTokenStorage sessionTokenStorage;

    public PositionBookService(WebClient webClient, AuthService authService, SessionTokenStorage sessionTokenStorage) {
        this.authService = authService;
        this.webClient = webClient;
        this.sessionTokenStorage = sessionTokenStorage;
    }

    public PositionBookResponse fetchPositionBook() {
        return webClient.get()
                .uri(POSITION_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("Sid", sessionTokenStorage.getSid())
                .header("Auth", sessionTokenStorage.getSessionToken())
                .header("neo-fin-key", API_SUB_KEY)
                .header("Authorization", "Bearer " + authService.getAccessToken())
                .retrieve()
                .bodyToMono(PositionBookResponse.class)
                .block();
    }
}
