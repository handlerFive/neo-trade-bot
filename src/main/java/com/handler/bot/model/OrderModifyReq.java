package com.handler.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderModifyReq {

    @JsonProperty("tk")
    private String token;

    @JsonProperty("mp")
    private String marketProtection;

    @JsonProperty("pc")
    private String productCode;

    @JsonProperty("dd")
    private String disclosedQty;

    @JsonProperty("dq")
    private String disclosedQuantity;

    @JsonProperty("vd")
    private String validity;

    @JsonProperty("ts")
    private String tradingSymbol;

    @JsonProperty("tt")
    private String transactionType;

    @JsonProperty("pr")
    private String price;

    @JsonProperty("tp")
    private String triggerPrice;

    @JsonProperty("qt")
    private String quantity;

    @JsonProperty("no")
    private String orderNumber;

    @JsonProperty("es")
    private String exchangeSegment;

    @JsonProperty("pt")
    private String priceType;
}
