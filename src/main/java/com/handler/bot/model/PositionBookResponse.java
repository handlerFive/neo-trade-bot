package com.handler.bot.model;

import java.util.List;

public class PositionBookResponse {
    private String stat;
    private int stCode;
    private List<PositionData> data;

    public String getStat() { return stat; }
    public void setStat(String stat) { this.stat = stat; }

    public int getStCode() { return stCode; }
    public void setStCode(int stCode) { this.stCode = stCode; }

    public List<PositionData> getData() { return data; }
    public void setData(List<PositionData> data) { this.data = data; }
}
