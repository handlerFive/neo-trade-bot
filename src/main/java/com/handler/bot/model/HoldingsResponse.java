package com.handler.bot.model;

import java.util.List;

public class HoldingsResponse {
    private List<HoldingData> data;

    public List<HoldingData> getData() {
        return data;
    }

    public void setData(List<HoldingData> data) {
        this.data = data;
    }
}
