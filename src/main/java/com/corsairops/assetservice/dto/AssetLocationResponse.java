package com.corsairops.assetservice.dto;

import com.corsairops.assetservice.model.AssetLocation;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssetLocationResponse(
        Long id,
        UUID assetId,
        Double longitude,
        Double latitude,
        LocalDateTime timestamp
) {

    public static AssetLocationResponse from(AssetLocation assetLocation) {
        return new AssetLocationResponse(
                assetLocation.getId(),
                assetLocation.getAsset().getId(),
                assetLocation.getLongitude(),
                assetLocation.getLatitude(),
                assetLocation.getTimestamp()
        );
    }
}