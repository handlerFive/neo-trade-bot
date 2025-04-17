package com.handler.bot.service;

import com.handler.bot.component.SessionTokenStorage;
import com.handler.bot.model.OrderReq;
import com.handler.bot.model.OrderRes;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.handler.bot.constants.Constant.API_SUB_KEY;
import static com.handler.bot.constants.Constant.ORDER_URL;

@Service
@Slf4j
public class OrderService {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final SessionTokenStorage sessionTokenStorage;
    private final AuthService authService;

    public OrderService(AuthService authService, WebClient webClient, SessionTokenStorage sessionTokenStorage) {
        this.webClient = webClient;
        this.authService = authService;
        this.sessionTokenStorage = sessionTokenStorage;
    }

    public OrderRes placeOrder(OrderReq orderReq) {
        String jDataJson = buildReqData(orderReq);
        return webClient.post()
                .uri(ORDER_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("Sid", sessionTokenStorage.getSid())
                .header("Auth", sessionTokenStorage.getSessionToken())
                .header("neo-fin-key", API_SUB_KEY)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("Authorization", "Bearer " + authService.getAccessToken())
                .bodyValue("jData=" + jDataJson)
                .retrieve()
                .bodyToMono(OrderRes.class)
                .doOnError(e -> log.error("Error placing order", e))
                .block();
    }

    private String buildReqData(OrderReq orderReq) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(orderReq);
        } catch (Exception e) {
            log.error("Error serializing order request", e);
            throw new RuntimeException("Failed to build jData", e);
        }
    }
}

