package com.handler.bot.model;

public class HoldingData {
    private String symbol;
    private String displaySymbol;
    private double averagePrice;
    private int quantity;
    private String exchangeSegment;
    private String exchangeIdentifier;
    private double holdingCost;
    private double mktValue;
    private String scripId;
    private int instrumentToken;
    private String instrumentType;
    private boolean isAlternateScrip;
    private double closingPrice;

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getDisplaySymbol() { return displaySymbol; }
    public void setDisplaySymbol(String displaySymbol) { this.displaySymbol = displaySymbol; }
    public double getAveragePrice() { return averagePrice; }
    public void setAveragePrice(double averagePrice) { this.averagePrice = averagePrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getExchangeSegment() { return exchangeSegment; }
    public void setExchangeSegment(String exchangeSegment) { this.exchangeSegment = exchangeSegment; }
    public String getExchangeIdentifier() { return exchangeIdentifier; }
    public void setExchangeIdentifier(String exchangeIdentifier) { this.exchangeIdentifier = exchangeIdentifier; }
    public double getHoldingCost() { return holdingCost; }
    public void setHoldingCost(double holdingCost) { this.holdingCost = holdingCost; }
    public double getMktValue() { return mktValue; }
    public void setMktValue(double mktValue) { this.mktValue = mktValue; }
    public String getScripId() { return scripId; }
    public void setScripId(String scripId) { this.scripId = scripId; }
    public int getInstrumentToken() { return instrumentToken; }
    public void setInstrumentToken(int instrumentToken) { this.instrumentToken = instrumentToken; }
    public String getInstrumentType() { return instrumentType; }
    public void setInstrumentType(String instrumentType) { this.instrumentType = instrumentType; }
    public boolean isAlternateScrip() { return isAlternateScrip; }
    public void setAlternateScrip(boolean alternateScrip) { isAlternateScrip = alternateScrip; }
    public double getClosingPrice() { return closingPrice; }
    public void setClosingPrice(double closingPrice) { this.closingPrice = closingPrice; }
}
