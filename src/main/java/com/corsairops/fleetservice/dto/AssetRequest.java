package com.corsairops.fleetservice.dto;

import com.corsairops.fleetservice.model.AssetStatus;
import com.corsairops.fleetservice.model.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AssetRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Type is required")
        AssetType type,

        @NotNull(message = "Status is required")
        AssetStatus status,

        Long fleetId
) {
}