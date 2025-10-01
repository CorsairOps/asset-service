package com.corsairops.assetservice.dto;

import com.corsairops.assetservice.model.Asset;
import com.corsairops.assetservice.model.AssetStatus;
import com.corsairops.assetservice.model.AssetType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssetResponse(
        UUID id,
        String name,
        AssetType type,
        AssetStatus status,
        Double longitude,
        Double latitutde,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    @JsonIgnore
    public static AssetResponse from(Asset asset) {
        return new AssetResponse(
                asset.getId(),
                asset.getName(),
                asset.getType(),
                asset.getStatus(),
                asset.getLongitude(),
                asset.getLatitude(),
                asset.getCreatedAt(),
                asset.getUpdatedAt()
        );
    }
}