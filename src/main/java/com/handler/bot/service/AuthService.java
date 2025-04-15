package com.handler.bot.service;

import com.handler.bot.component.TokenStorage;
import com.handler.bot.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.wso2.client.api.ApiClient;
import org.wso2.client.api.ApiException;
import org.wso2.client.api.Configuration;
import org.wso2.client.api.Login.TotpApi;
import org.wso2.client.api.auth.OAuth;
import org.wso2.client.model.Login.InlineObject4;
import org.wso2.client.model.Login.InlineObject5;
import org.wso2.client.model.Login.InlineResponse2003;
import org.wso2.client.model.Login.InlineResponse2004;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.handler.bot.constants.Constant.*;

@Slf4j
@Service
public class AuthService {

    @Value("${CLIENT_KEY}")
    private String clientKey;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Value("${CLIENT_PIN}")
    private String clientPin;

    private String accessToken;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String AUTH_URL = Constant.AUTH_BASE_URL + "token";
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final TokenStorage tokenStorage;

    public AuthService(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    public String generateBasicToken() {
        String auth = clientKey + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedAuth);
        String requestBody = "grant_type=client_credentials";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(AUTH_URL, HttpMethod.POST, requestEntity, Map.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            accessToken = (String) response.getBody().get("access_token");
            return accessToken;
        } else {
            throw new RuntimeException("Authentication failed: " + response.getStatusCode());
        }
    }

    public String getAccessToken() {
        return accessToken == null ? generateBasicToken() : accessToken;
    }

    public String validateUser(String accessToken, String mobileNumber, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobileNumber", mobileNumber);
        requestBody.put("password", password);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("User '{}' authenticated successfully.", response.getStatusCode());
                return response.getBody();
            } else {
                logger.warn("Validation failed for user with status code '{}'", response.getStatusCode());
                throw new RuntimeException("Validation failed: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Validation error: {} ", e.getMessage(), e);
            throw new RuntimeException("Error during validation: " + e.getMessage());
        }
    }

    public Optional<InlineResponse2003> generateViewToken(InlineObject4 payload) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(TOTP_LOGIN_BASE_URL);
        OAuth oAuth = (OAuth) defaultClient.getAuthentication("default");
        oAuth.setAccessToken(getAccessToken());
        TotpApi apiInstance = new TotpApi(defaultClient);
        try {
            InlineResponse2003 response = apiInstance.loginV6TotpLoginPost(NEOTRADEAPI, payload);
            return Optional.ofNullable(response);
        } catch (ApiException e) {
            logger.error("TOTP login failed. Status code: {}, Reason: {}, Response: {}",
                    e.getCode(), e.getMessage(), e.getResponseBody(), e);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Unexpected exception during TOTP login", e);
            return Optional.empty();
        }
    }

    public Optional<InlineResponse2004> generateFinalSessionToken(String sId, String authToken) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(TOTP_LOGIN_BASE_URL);
        OAuth oAuth = (OAuth) defaultClient.getAuthentication("default");
        oAuth.setAccessToken(generateBasicToken());
        TotpApi apiInstance = new TotpApi(defaultClient);
        try {
            InlineObject5 payload = new InlineObject5();
            payload.setMpin(clientPin);
            InlineResponse2004 inlineResponse2004 = apiInstance.loginV6TotpValidatePost(sId, authToken, NEOTRADEAPI, payload);
            return Optional.ofNullable(inlineResponse2004);
        } catch (ApiException e) {
            logger.error("TOTP login failed. Status code: {}, Reason: {}, Response: {}",
                    e.getCode(), e.getMessage(), e.getResponseBody(), e);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Unexpected exception during TOTP login", e);
            return Optional.empty();
        }
    }

    public String getValidToken() {
        if (tokenStorage.isTokenExpired()) {
            logger.info("Token expired. Refreshing...");
            return refreshAccessToken();
        }
        return tokenStorage.getAccessToken();
    }

    public String refreshAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("CLIENT_KEY", "CLIENT_SECRET");

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);
        ResponseEntity<Map> response = restTemplate.exchange(AUTH_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map responseBody = response.getBody();
            assert responseBody != null;
            String newToken = (String) responseBody.get("access_token");
            int expiresIn = (int) responseBody.get("expires_in");
            tokenStorage.updateToken(newToken, expiresIn);
            logger.info("Token refreshed successfully");
            return newToken;
        } else {
            throw new RuntimeException("Failed to refresh token: " + response.getStatusCode());
        }
    }
}
