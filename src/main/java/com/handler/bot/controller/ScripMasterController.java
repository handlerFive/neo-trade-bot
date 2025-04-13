package com.handler.bot.controller;

import com.handler.bot.component.SessionTokenStorage;
import com.handler.bot.component.TokenStorage;
import com.handler.bot.service.ScripMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/s-master")
public class ScripMasterController {

    private final ScripMasterService scripMasterService;
    private final TokenStorage tokenStorage;
    private static final Logger logger = LoggerFactory.getLogger(ScripMasterController.class);

    public ScripMasterController(ScripMasterService scripMasterService, TokenStorage tokenStorage, SessionTokenStorage sessionTokenStorage) {
        this.scripMasterService = scripMasterService;
        this.tokenStorage = tokenStorage;
    }

    @GetMapping("/files")
    public void getScripMasterFiles() {
        if (tokenStorage != null && !tokenStorage.getAccessToken().isEmpty())
            scripMasterService.fetchAndStoreMasterScrips(tokenStorage.getAccessToken());
        else logger.error("No Session Token");

    }
}

