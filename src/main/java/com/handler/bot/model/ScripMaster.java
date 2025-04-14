package com.handler.bot.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ScripMaster {
    @Getter
    @Setter
    private String pSymbol;
    private String pTrdSymbol;
    private String pScripRefKey;
    private String pExSeg;
    private String pInstType;
    private String pOptionType;
    private double dStrikePrice;
    private int lLotSize;
    private long lExpiryDate;

    @Override
    public String toString() {
        return "ScripMaster{" +
                "pSymbol='" + pSymbol + '\'' +
                ", pTrdSymbol='" + pTrdSymbol + '\'' +
                ", pScripRefKey='" + pScripRefKey + '\'' +
                ", pExSeg='" + pExSeg + '\'' +
                ", pInstType='" + pInstType + '\'' +
                ", pOptionType='" + pOptionType + '\'' +
                ", dStrikePrice=" + dStrikePrice +
                ", lLotSize=" + lLotSize +
                ", lExpiryDate=" + lExpiryDate +
                '}';
    }
}
