package com.handler.bot.controller;

import com.handler.bot.component.TokenStorage;
import com.handler.bot.model.TotpLoginReq;
import com.handler.bot.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wso2.client.model.Login.InlineResponse2003;
import org.wso2.client.model.Login.InlineResponse2003Data;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BotController {

    private final AuthService authService;
    private final TokenStorage tokenStorage;
    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    public BotController(AuthService authService, TokenStorage tokenStorage) {
        this.authService = authService;
        this.tokenStorage = tokenStorage;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Bot is live");
    }

    //Token Generation - Basic Auth
    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticate() {
        String accessToken = authService.generateBasicToken();
        if (tokenStorage.isTokenExpired() || tokenStorage.getAccessToken().isEmpty()) {
            tokenStorage.updateToken(accessToken, 1800000L); //30 minutes
        } else {
            return ResponseEntity.ok("Existing Token " + tokenStorage.getAccessToken());
        }
        return ResponseEntity.ok("Auth Token " + accessToken);
    }

    //Totp Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody TotpLoginReq totpLoginReq) {
        Optional<InlineResponse2003> result = authService.loginWithTOTP(totpLoginReq);
        if (result.isPresent()) {
            InlineResponse2003Data data = result.get().getData();
            logger.info("totpLoginRes : {}", data);
            return ResponseEntity.ok("TOTP Token Generated");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid credentials or OTP.");
        }
    }

    @GetMapping("/token")
    public String getValidToken() {
        return authService.getValidToken();
    }

    //Fetch Market Data
    @GetMapping("/market")
    public ResponseEntity<String> fetchMarketData() {
        // Call Market Data Service (To be implemented)
        return ResponseEntity.ok("Market Data Response");
    }

    //Place Order
    @PostMapping("/trade")
    public ResponseEntity<String> placeTrade(@RequestBody String tradeRequest) {
        // Call Order Execution Service (To be implemented)
        return ResponseEntity.ok("Trade Executed");
    }

    //Check Order Status
    @GetMapping("/order/{orderId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String orderId) {
        // Call Order Status Service (To be implemented)
        return ResponseEntity.ok("Order Status for " + orderId);
    }
}
