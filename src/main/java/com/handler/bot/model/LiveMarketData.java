package com.handler.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveMarketData {
    private String exchangeToken;
    private String displaySymbol;
    private String exchange;
    private long lastUpdateTime;
    private double ltp;
    private int lastTradedQuantity;
    private int totalBuy;
    private int totalSell;
    private int lastVolume;
    private double avgCost;
    private double openInt;
    private double change;
    private double percentageChange;
    private double lowPriceRange;
    private double highPriceRange;
    private double yearHigh;
    private double yearLow;
}
