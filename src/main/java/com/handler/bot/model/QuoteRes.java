package com.handler.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteRes {
    private String exchange_token;
    private String display_symbol;
    private String exchange;
    private String lstup_time;
    private String ltp;
    private String last_traded_quantity;
    private String total_buy;
    private String total_sell;
    private String last_volume;
    private String avg_cost;
    private String open_int;
    private String change;
    private String per_change;
    private String low_price_range;
    private String high_price_range;
    private String year_high;
    private String year_low;
    private OHLC ohlc;
    private Depth depth;

    @Data
    public static class OHLC {
        private String open;
        private String high;
        private String low;
        private String close;
    }

    @Data
    public static class Depth {
        private List<DepthLevel> buy;
        private List<DepthLevel> sell;
    }

    @Data
    public static class DepthLevel {
        private String price;
        private String quantity;
        private String orders;
    }
} 