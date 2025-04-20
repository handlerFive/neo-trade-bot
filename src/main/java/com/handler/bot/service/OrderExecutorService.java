package com.handler.bot.service;

import com.handler.bot.marketdata.MarketDataFetcher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.handler.bot.constants.Constant.ORDER_PLACE_URL;

@Service
public class OrderExecutorService {

    private static final int STOP_LOSS_PERCENT = 2;
    private static final int TAKE_PROFIT_PERCENT = 5;
    private final MarketDataFetcher marketDataFetcher;
    private final EmaService strategy;
    private final AuthService authService;
    private final RestTemplate restTemplate;

    public OrderExecutorService(MarketDataFetcher marketDataFetcher, EmaService strategy, AuthService authService) {
        this.marketDataFetcher = marketDataFetcher;
        this.strategy = strategy;
        this.authService = authService;
        this.restTemplate = new RestTemplate();
    }

    public void evaluateAndExecute(String symbol) {
        String marketData = marketDataFetcher.fetchMarketData(symbol);
        double latestPrice = extractPriceFromData(marketData);

        strategy.addPrice(latestPrice);

        if (strategy.shouldBuy(latestPrice)) {
            placeOrder(symbol, "BUY", latestPrice);
        } else if (strategy.shouldSell(latestPrice)) {
            placeOrder(symbol, "SELL", latestPrice);
        }
    }


    private void placeOrder(String symbol, String orderType, double price) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> orderDetails = getStringObjectMap(symbol, orderType, price);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(orderDetails, headers);
        ResponseEntity<String> response = restTemplate.exchange(ORDER_PLACE_URL, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Order placed successfully: " + response.getBody());
        } else {
            System.err.println("Failed to place order: " + response.getBody());
        }
    }

    private static Map<String, Object> getStringObjectMap(String symbol, String orderType, double price) {

        double stopLoss = orderType.equals("BUY") ? price * (1 - STOP_LOSS_PERCENT) : price * (1 + STOP_LOSS_PERCENT);
        double takeProfit = orderType.equals("BUY") ? price * (1 + TAKE_PROFIT_PERCENT) : price * (1 - TAKE_PROFIT_PERCENT);

        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("symbol", symbol);
        orderDetails.put("orderType", orderType);
        orderDetails.put("price", price);
        orderDetails.put("quantity", 1); // Adjust quantity as needed
        orderDetails.put("stopLoss", stopLoss);
        orderDetails.put("takeProfit", takeProfit);
        return orderDetails;
    }

    private double extractPriceFromData(String marketData) {
        // Dummy implementation: Extract price from API response
        return Double.parseDouble(marketData); // Replace with JSON parsing logic
    }
}
