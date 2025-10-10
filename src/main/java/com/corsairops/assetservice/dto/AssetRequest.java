package com.corsairops.assetservice.dto;

import com.corsairops.assetservice.model.AssetStatus;
import com.corsairops.assetservice.model.AssetType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
        @Min(value = -180, message = "Longitude must be between -180 and 180")
        @Max(value = 180, message = "Longitude must be between -180 and 180")
        Double longitude,

        @NotNull(message = "Latitude is required")
        @Min(value = -90, message = "Latitude must be between -90 and 90")
        @Max(value = 90, message = "Latitude must be between -90 and 90")
        Double latitude
) {
}