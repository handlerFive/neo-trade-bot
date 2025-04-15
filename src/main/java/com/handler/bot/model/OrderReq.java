package com.handler.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReq {

    @JsonProperty("am")
    private String afterMarketOrder;

    @JsonProperty("dq")
    private String disclosedQuantity;

    @JsonProperty("es")
    private String exchangeSegment;

    @JsonProperty("mp")
    private String marketProtection;

    @JsonProperty("pc")
    private String productCode;

    @JsonProperty("pf")
    private String portfolioFlag;

    @JsonProperty("pr")
    private String price;

    @JsonProperty("pt")
    private String priceType;

    @JsonProperty("qt")
    private String quantity;

    @JsonProperty("rt")
    private String retentionType;

    @JsonProperty("tp")
    private String triggerPrice;

    @JsonProperty("ts")
    private String tradingSymbol;

    @JsonProperty("tt")
    private String transactionType;
}
