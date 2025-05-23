package com.handler.bot.controller;

import com.handler.bot.component.SessionTokenStorage;
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
import org.wso2.client.model.Login.InlineResponse2004;
import java.util.Optional;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final TokenStorage tokenStorage;
    private final SessionTokenStorage sessionTokenStorage;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, TokenStorage tokenStorage, SessionTokenStorage sessionTokenStorage) {
        this.authService = authService;
        this.tokenStorage = tokenStorage;
        this.sessionTokenStorage = sessionTokenStorage;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Bot is live");
    }

    //Token Generation - Basic Auth
    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticate() {
        String accessToken = authService.generateBasicToken();
        tokenStorage.updateToken(accessToken, 18000L);
        return ResponseEntity.ok("Basic View Token Generated : " + escapeHtml4(accessToken));
    }

    //Totp Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody TotpLoginReq totpLoginReq) {
        Optional<InlineResponse2003> result = authService.generateViewToken(totpLoginReq);
        if (result.isPresent()) {
            InlineResponse2003Data data = result.get().getData();
            logger.info("totpLoginRes : {}", data);
            Optional<InlineResponse2004> finalSessionData;
            if (data != null) {
                finalSessionData = authService.generateFinalSessionToken(data.getSid(), data.getToken());
                if (finalSessionData.isPresent() && finalSessionData.get().getData() != null) {
                    String sessionToken = finalSessionData.get().getData().getToken();
                    String sId = finalSessionData.get().getData().getSid();
                    sessionTokenStorage.setSessionToken(sessionToken);
                    sessionTokenStorage.setSid(sId);
                    logger.info("Final Session Token Stored : {}", sessionTokenStorage.getSessionToken());
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MPIN Validation Failed.");
                }
            }
            return ResponseEntity.ok("TOTP Token Generated " + sessionTokenStorage.getSessionToken());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid credentials or OTP.");
        }
    }

    @GetMapping("/token")
    public String getValidToken() {
        String token = authService.getValidToken();
        return escapeHtml4(token);
    }
}
