package com.handler.bot.service;

import com.handler.bot.model.ScripMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MasterScripLoader {

    private static final Logger logger = LoggerFactory.getLogger(MasterScripLoader.class);

    public Map<String, ScripMaster> parseScripMaster(String filePath) {
        Map<String, ScripMaster> scripMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) throw new RuntimeException("CSV is empty!");
            String[] headers = headerLine.split(",", -1);
            Map<String, Integer> headerIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerIndex.put(headers[i].trim(), i);
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                ScripMaster dto = new ScripMaster();
                dto.setTk(getValue(headerIndex, values, "pSymbol"));
                dto.setSymbol(getValue(headerIndex, values, "pSymbolName"));
                dto.setTradingSymbol(getValue(headerIndex, values, "pTrdSymbol"));
                dto.setExchange(getValue(headerIndex, values, "pExchSeg"));
                dto.setInstrumentType(getValue(headerIndex, values, "pInstType"));
                dto.setScripRefKey(getValue(headerIndex, values, "pScripRefKey"));
                dto.setISin(getValue(headerIndex, values, "pISIN"));
                dto.setAssetCode(getValue(headerIndex, values, "pAssetCode"));
                dto.setExpiryDate(getValue(headerIndex, values, "lExpiryDate"));
                dto.setLotSize(getValue(headerIndex, values, "lLotSize"));
                dto.setDescription(getValue(headerIndex, values, "pDesc"));
                dto.setOptionType(getValue(headerIndex, values, "pOptionType"));
                if (dto.getTradingSymbol() != null) {
                    scripMap.put(dto.getTradingSymbol(), dto);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading Scrip Master CSV: ", e);
        }
        return scripMap;
    }

    private String getValue(Map<String, Integer> headerIndex, String[] values, String columnName) {
        Integer index = headerIndex.get(columnName);
        if (index != null && index < values.length) {
            return values[index].trim();
        }
        return null;
    }

    public List<ScripMaster> findOptionsBySymbol(Map<String, ScripMaster> scripMap, String symbol, String optionType) {
        return scripMap.values().stream()
                .filter(s -> s.getSymbol() != null && s.getSymbol().equalsIgnoreCase(symbol))
                .peek(scripMaster -> logger.info("Scrip Display 1 : {}" , scripMaster))
                .filter(s -> s.getOptionType() != null && s.getOptionType().equalsIgnoreCase(optionType))
                .peek(scripMaster -> logger.info("Scrip Display 2 : {}" , scripMaster))
                .collect(Collectors.toList());
    }
}
