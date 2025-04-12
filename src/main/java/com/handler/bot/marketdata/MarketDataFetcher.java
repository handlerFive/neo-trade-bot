package com.handler.bot.marketdata;

import com.handler.bot.service.AuthService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.handler.bot.constants.Constant.MARKET_DATA_URL;

@Service
public class MarketDataFetcher {

    private final AuthService authService;
    private final RestTemplate restTemplate;
    public MarketDataFetcher(AuthService authService) {
        this.authService = authService;
        this.restTemplate = new RestTemplate();
    }
    public String fetchMarketData(String symbol) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getAccessToken());
        HttpEntity<String> request = new HttpEntity<>(headers);

        String url = MARKET_DATA_URL + "?symbol=" + symbol;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch market data: " + response.getBody());
        }
    }
}
