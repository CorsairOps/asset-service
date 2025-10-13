package com.corsairops.assetservice.listeners;

import com.corsairops.assetservice.dto.AssetLocationRequest;
import com.corsairops.assetservice.service.AssetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AssetService assetService;

    @KafkaListener(id = "asset-location-listener", topics = "asset-location-updates")
    public void assetLocationListener(String message) {
        try {
        // Handle the incoming message
        log.info("Received message on topic asset-location-updates: " + message);
        AssetLocationRequest request = objectMapper.readValue(message, AssetLocationRequest.class);
            assetService.changeAssetLocation(request.assetId(), request);
            log.info("Processed asset location update for asset ID: " + request.assetId());
        } catch (Exception e) {
            log.error("Error processing asset location update. Message: " + message, e);
        }
    }
}