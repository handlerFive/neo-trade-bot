package com.handler.bot.model;

import lombok.Data;

@Data
public class ScripMaster {

    private String tk;               // Token ID
    private String symbol;           // pSymbol
    private String tradingSymbol;    // pTrdSymbol
    private String exchange;         // pExchangeSeg
    private String instrumentType;   // pInstType
    private String scripRefKey;      // pScripRefKey
    private String iSin;             // piSIN
    private String assetCode;        // pAssetCode
    private String expiryDate;       // pExpiryDate
    private String lotSize;          // lLotSize
    private String description;      // pDesc
    private String optionType;       // pOptionType
}
