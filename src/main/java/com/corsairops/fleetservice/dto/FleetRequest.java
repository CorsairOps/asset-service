package com.corsairops.fleetservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FleetRequest(
        @NotNull(message = "Fleet name cannot be null")
        @Size(max = 255, message = "Fleet name must be at most 255 characters")
        String name,
        String description
) {

}