package com.corsairops.fleetservice.dto;

import com.corsairops.fleetservice.model.Fleet;

import java.time.LocalDateTime;
import java.util.List;

public record FleetResponse(
        Long id,
        String name,
        String description,
        List<AssetResponse> assets,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static FleetResponse from(Fleet fleet) {
        return new FleetResponse(
                fleet.getId(),
                fleet.getName(),
                fleet.getDescription(),
                fleet.getAssets() != null ? fleet.getAssets().stream()
                        .map(AssetResponse::from)
                        .toList() : null,
                fleet.getCreatedAt(),
                fleet.getUpdatedAt()
        );
    }
}