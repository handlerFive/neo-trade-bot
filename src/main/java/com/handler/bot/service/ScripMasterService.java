package com.handler.bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.handler.bot.constants.Constant.*;

@Service
public class ScripMasterService {

    private static final Logger logger = LoggerFactory.getLogger(ScripMasterService.class);
    private static final OkHttpClient client = new OkHttpClient();

    public void fetchAndStoreMasterScrips(String sessionToken) {
        Request request = new Request.Builder()
                .url(SCRIP_MASTER_DOWNLOAD_URL)
                .get()
                .addHeader("accept", "*/*")
                .addHeader("Authorization", "Bearer " + sessionToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                List<String> filePaths = extractFilePaths(jsonResponse);
                filePaths.forEach(x -> logger.info("File : {}", x));
                if (!filePaths.isEmpty()) {
                    filePaths.forEach(ScripMasterService::downloadFile);
                } else {
                    logger.info("No files to download.");
                }
            } else {
                logger.error("Failed to fetch data: {} {}", response.code(), response.message());
            }
        } catch (IOException e) {
            logger.error("Exception ", e);
        }
    }

    private List<String> extractFilePaths(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);
        JsonNode filesPathsNode = root.path("data").path("filesPaths");
        List<String> filePaths = new ArrayList<>();
        if (filesPathsNode.isArray()) {
            for (JsonNode urlNode : filesPathsNode) {
                filePaths.add(urlNode.asText());
            }
        }
        return filePaths;
    }

    private static void downloadFile(String fileUrl) {
        Request request = new Request.Builder()
                .url(fileUrl)
                .get()
                .build();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String saveDir = new File(CSV_DOWNLOAD_PATH).getAbsolutePath() + File.separator;
        boolean isCreated = new File(saveDir).mkdirs();
        File targetFile = new File(saveDir + fileName);
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream outputStream = new FileOutputStream(targetFile + fileName);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                logger.info("Downloaded: {}", fileName);
            } else {
                logger.info("Failed to download: {} Status: {}", fileUrl, response.code());
            }
        } catch (IOException e) {
            logger.error("Error downloading : {} {}", fileUrl, e.getMessage());
        }
    }
}

