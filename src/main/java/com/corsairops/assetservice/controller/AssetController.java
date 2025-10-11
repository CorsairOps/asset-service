package com.corsairops.assetservice.controller;

import com.corsairops.assetservice.dto.AssetLocationResponse;
import com.corsairops.assetservice.dto.AssetRequest;
import com.corsairops.assetservice.dto.AssetResponse;
import com.corsairops.assetservice.service.AssetService;
import com.corsairops.shared.annotations.CommonReadResponses;
import com.corsairops.shared.annotations.CommonWriteResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Asset Management", description = "APIs for managing assets")
@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @Operation(summary = "Create a new asset")
    @CommonWriteResponses
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssetResponse createAsset(@RequestBody @Valid AssetRequest assetRequest) {
        return AssetResponse.from(assetService.createAsset(assetRequest));
    }

    @Operation(summary = "Get all assets")
    @CommonReadResponses
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AssetResponse> getAllAssets() {
        return assetService.getAllAssets().stream()
                .map(AssetResponse::from)
                .toList();
    }

    @Operation(summary = "Get count of all assets")
    @CommonReadResponses
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Long getAssetCount() {
        return assetService.getAssetCount();
    }

    @Operation(summary = "Get an asset by ID")
    @CommonReadResponses
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponse getAssetById(@PathVariable("id") UUID id) {
        return AssetResponse.from(assetService.getAssetById(id));
    }

    @Operation(summary = "Get assets by a list of IDs")
    @CommonReadResponses
    @GetMapping("/ids")
    @ResponseStatus(HttpStatus.OK)
    public List<AssetResponse> getAssetsByIds(@RequestParam String ids) {
        Set<UUID> idSet = Set.of(ids.split(",")).stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        return assetService.getAssetsByIds(idSet).stream()
                .map(AssetResponse::from)
                .toList();
    }

    @Operation(summary = "Get asset location history by asset ID")
    @CommonReadResponses
    @GetMapping("/{id}/locations")
    @ResponseStatus(HttpStatus.OK)
    public List<AssetLocationResponse> getAssetLocations(@PathVariable("id") UUID id) {
        return assetService.getAssetLocations(id).stream()
                .map(AssetLocationResponse::from)
                .toList();
    }

    @Operation(summary = "Update an existing asset")
    @CommonWriteResponses
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponse updateAsset(@PathVariable("id") UUID id, @RequestBody @Valid AssetRequest assetRequest) {
        return AssetResponse.from(assetService.updateAsset(id, assetRequest));
    }

    @Operation(summary = "Delete an asset by ID")
    @CommonWriteResponses
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAsset(@PathVariable("id") UUID id) {
        assetService.deleteAsset(id);
    }
}