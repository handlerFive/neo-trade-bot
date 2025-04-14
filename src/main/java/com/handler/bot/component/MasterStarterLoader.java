package com.handler.bot.component;

import com.handler.bot.model.ScripMaster;
import com.handler.bot.service.AuthService;
import com.handler.bot.service.MasterScripLoader;
import com.handler.bot.service.ScripMasterService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.handler.bot.constants.Constant.CSV_DOWNLOAD_PATH;

@Component
public class MasterStarterLoader {

    private final MasterScripLoader masterScripLoader;
    private static final Logger logger = LoggerFactory.getLogger(MasterStarterLoader.class);
    private final ScripMasterService scripMasterService;
    private final AuthService authService;
    @Getter
    private List<ScripMaster> scripMasterList;

    public MasterStarterLoader(MasterScripLoader masterScripLoader, List<ScripMaster> scripMasterList, ScripMasterService scripMasterService, TokenStorage tokenStorage, AuthService authService) {
        this.masterScripLoader = masterScripLoader;
        this.scripMasterService = scripMasterService;
        this.authService = authService;
    }

    @PostConstruct
    public void init() {
        logger.info("MasterStarterLoader");
        scripMasterService.fetchAndStoreMasterScrips(authService.getAccessToken());
        scripMasterList = masterScripLoader.loadFromCsv(CSV_DOWNLOAD_PATH + "/nse_fo.csvnse_fo.csv");
        logger.info("Scrip Master List : {}", scripMasterList.toString());
    }
}
