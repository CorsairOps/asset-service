package com.corsairops.fleetservice.dto;

import com.corsairops.fleetservice.model.Asset;
import com.corsairops.fleetservice.model.AssetStatus;
import com.corsairops.fleetservice.model.AssetType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssetResponse(
        UUID id,
        String name,
        AssetType type,
        AssetStatus status,
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
                asset.getCreatedAt(),
                asset.getUpdatedAt()
        );
    }
}