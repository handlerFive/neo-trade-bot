package com.handler.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderRes {
    private String result;
    private String stat;
}
