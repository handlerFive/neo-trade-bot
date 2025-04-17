package com.handler.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRes {
    @JsonProperty("nOrdNo")
    private String nOrdNo;
    private String stat;
    private int stCode;
}
