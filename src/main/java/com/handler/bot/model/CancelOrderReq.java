package com.handler.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderReq {
    private String am;  // AMO - YES / NO
    private String on;  // Order Number
    private String ts;  // Trading Symbol
}
