package com.corsairops.fleetservice.controller;

import com.corsairops.fleetservice.dto.AssetRequest;
import com.corsairops.fleetservice.dto.AssetResponse;
import com.corsairops.fleetservice.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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