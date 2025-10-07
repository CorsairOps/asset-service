package com.corsairops.assetservice.dto;

import com.corsairops.assetservice.model.AssetStatus;
import com.corsairops.assetservice.model.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AssetRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Type is required")
        AssetType type,

        @NotNull(message = "Status is required")
        AssetStatus status,

        @NotNull(message = "Longitude is required")
        Double longitude,

        @NotNull(message = "Latitude is required")
        Double latitude
) {
}