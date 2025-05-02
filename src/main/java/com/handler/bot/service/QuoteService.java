package com.handler.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.wso2.client.api.ApiClient;
import org.wso2.client.api.ApiException;
import org.wso2.client.api.Configuration;
import org.wso2.client.api.Quotes_API.DefaultApi;
import org.wso2.client.api.auth.OAuth;
import org.wso2.client.model.Quotes_API.InlineResponse200;

import java.util.List;

import static com.handler.bot.constants.Constant.QUOTES_URL;

@Service
public class QuoteService {
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    public QuoteService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * String | Type of data to retrieve.
     * Options include `ltp`, `depth`, `oi`, `ohlc`, `circuit_limits`, `scrip_details`, `52W`, or `all`.
     * Defaults to `all`.
     * neoSymbols = "nse_cm|2885,bse_cm|532174,nse_fo|65500,bse_cm|540376";
     * String | A comma-separated list of neo symbols to fetch quotes for.
     **/
    public List<InlineResponse200> getQuotes(String symbols) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(QUOTES_URL);
        OAuth oAuth = (OAuth) defaultClient.getAuthentication("default");
        oAuth.setAccessToken(authService.generateBasicToken());
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String quoteType = "all";
        List<InlineResponse200> result = List.of();
        try {
            result = apiInstance.quotesNeosymbolNeoSymbolsQuoteTypeGet(symbols, quoteType);
            System.out.println(result);
        } catch (ApiException e) {
            logger.error("Exception when calling DefaultApi#quotesNeosymbolNeoSymbolsQuoteTypeGet");
            logger.error("Status code: {}", e.getCode());
            logger.error("Reason: {}", e.getResponseBody());
            logger.error("Response headers: {}", e.getResponseHeaders());
            logger.error(e.getMessage());
        }
        return result;
    }
} 