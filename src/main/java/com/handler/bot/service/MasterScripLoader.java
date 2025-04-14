package com.handler.bot.service;

import com.handler.bot.model.ScripMaster;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterScripLoader {

    private static final Logger logger = LoggerFactory.getLogger(MasterScripLoader.class);

    public List<ScripMaster> loadFromCsv(String filePath) {
        List<ScripMaster> scripList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip header
                    continue;
                }
                String[] tokens = line.split(",");
                if (tokens.length < 60) {
                    continue;
                }
                ScripMaster scrip = getScripMaster(tokens);
                scripList.add(scrip);
            }
        } catch (Exception e) {
            logger.info("Exception in Master Scrip Loader: ", e);
        }
        return scripList;
    }

    @NotNull
    private static ScripMaster getScripMaster(String[] tokens) {
        ScripMaster scrip = new ScripMaster();
        scrip.setPSymbol(tokens[4]);
        scrip.setPTrdSymbol(tokens[5]);
        scrip.setPScripRefKey(tokens[7]);
        scrip.setPExSeg(tokens[3]);
        scrip.setPInstType(tokens[2]);
        scrip.setPOptionType(tokens[6]);
        try {
            scrip.setDStrikePrice(Double.parseDouble(tokens[15]));
        } catch (Exception e) {
            scrip.setDStrikePrice(0);
        }
        try {
            scrip.setLLotSize(Integer.parseInt(tokens[16]));
        } catch (Exception e) {
            scrip.setLLotSize(0);
        }
        try {
            scrip.setLExpiryDate(Long.parseLong(tokens[17]));
        } catch (Exception e) {
            scrip.setLExpiryDate(0);
        }
        return scrip;
    }

    public List<ScripMaster> findOptionsBySymbol(List<ScripMaster> list, String symbol, String optionType) {
        return list.stream()
                .filter(s -> s.getPSymbol().equalsIgnoreCase(symbol))
                .filter(s -> s.getPOptionType().equalsIgnoreCase(optionType))
                .collect(Collectors.toList());
    }
}
