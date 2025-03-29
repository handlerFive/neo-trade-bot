package com.handler.bot.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class EmaService {
    private static final int EMA_PERIOD = 5;
    private final LinkedList<Double> priceData = new LinkedList<>();

    public void addPrice(double price) {
        if (priceData.size() >= EMA_PERIOD) {
            priceData.removeFirst();
        }
        priceData.add(price);
    }

    public double calculateEMA() {
        if (priceData.size() < EMA_PERIOD) {
            throw new IllegalStateException("Not enough data to calculate EMA");
        }

        double multiplier = 2.0 / (EMA_PERIOD + 1);
        double ema = priceData.getFirst();
        for (int i = 1; i < priceData.size(); i++) {
            ema = ((priceData.get(i) - ema) * multiplier) + ema;
        }
        return ema;
    }

    public boolean shouldBuy(double latestPrice) {
        double ema = calculateEMA();
        return latestPrice > ema;
    }

    public boolean shouldSell(double latestPrice) {
        double ema = calculateEMA();
        return latestPrice < ema;
    }
}
