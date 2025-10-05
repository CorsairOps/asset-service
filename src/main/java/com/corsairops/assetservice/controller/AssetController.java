package com.corsairops.assetservice.controller;

import com.corsairops.assetservice.dto.AssetLocationResponse;
import com.corsairops.assetservice.dto.AssetRequest;
import com.corsairops.assetservice.dto.AssetResponse;
import com.corsairops.assetservice.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssetResponse createAsset(@RequestBody @Valid AssetRequest assetRequest) {
        return AssetResponse.from(assetService.createAsset(assetRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AssetResponse> getAllAssets() {
        return assetService.getAllAssets().stream()
                .map(AssetResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponse getAssetById(@PathVariable("id") UUID id) {
        return AssetResponse.from(assetService.getAssetById(id));
    }

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

    @GetMapping("/{id}/locations")
    @ResponseStatus(HttpStatus.OK)
    public List<AssetLocationResponse> getAssetLocations(@PathVariable("id") UUID id) {
        return assetService.getAssetLocations(id).stream()
                .map(AssetLocationResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponse updateAsset(@PathVariable("id") UUID id, @RequestBody @Valid AssetRequest assetRequest) {
        return AssetResponse.from(assetService.updateAsset(id, assetRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAsset(@PathVariable("id") UUID id) {
        assetService.deleteAsset(id);
    }
}