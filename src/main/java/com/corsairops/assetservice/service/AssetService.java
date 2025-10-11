package com.corsairops.assetservice.service;

import com.corsairops.assetservice.dto.AssetRequest;
import com.corsairops.assetservice.exception.AssetNameConflictException;
import com.corsairops.assetservice.exception.AssetNotFoundException;
import com.corsairops.assetservice.model.Asset;
import com.corsairops.assetservice.model.AssetLocation;
import com.corsairops.assetservice.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;

    @Transactional(readOnly = true)
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Asset getAssetById(UUID id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Asset> getAssetsByIds(Set<UUID> ids) {
        return assetRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Long getAssetCount() {
        return assetRepository.count();
    }

    @Transactional
    public Asset createAsset(AssetRequest assetRequest) {
        validateUniqueName(assetRequest.name());

        Asset asset = Asset.builder()
                .name(assetRequest.name())
                .type(assetRequest.type())
                .status(assetRequest.status())
                .longitude(assetRequest.longitude())
                .latitude(assetRequest.latitude())
                .assetLocations(new ArrayList<>())
                .build();

        updateAssetLocation(asset, assetRequest.longitude(), assetRequest.latitude());

        return assetRepository.save(asset);
    }

    @Transactional
    public Asset updateAsset(UUID id, AssetRequest assetRequest) {
        Asset existingAsset = getAssetById(id);
        validateUniqueName(assetRequest.name(), id);

        existingAsset.setName(assetRequest.name());
        existingAsset.setType(assetRequest.type());
        existingAsset.setStatus(assetRequest.status());

        if (!existingAsset.getLongitude().equals(assetRequest.longitude()) ||
                !existingAsset.getLatitude().equals(assetRequest.latitude())) {
            updateAssetLocation(existingAsset, assetRequest.longitude(), assetRequest.latitude());
        }

        return assetRepository.save(existingAsset);
    }

    @Transactional
    public void deleteAsset(UUID id) {
        if (!assetRepository.existsById(id)) {
            throw new AssetNotFoundException("Asset not found", HttpStatus.NOT_FOUND);
        }
        assetRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AssetLocation> getAssetLocations(UUID assetId) {
        var asset = getAssetById(assetId);
        List<AssetLocation> locations = new ArrayList<>(asset.getAssetLocations());
        locations.sort(Comparator.comparing(AssetLocation::getTimestamp).reversed());
        return locations;
    }

    private void validateUniqueName(String name) {
        if (assetRepository.existsByNameIgnoreCase(name)) {
            throw new AssetNameConflictException("Asset name already exists", HttpStatus.CONFLICT);
        }
    }

    private void validateUniqueName(String name, UUID id) {
        if (assetRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new AssetNameConflictException("Asset name already exists", HttpStatus.CONFLICT);
        }
    }

    private void updateAssetLocation(Asset asset, Double longitude, Double latitude) {
        asset.setLongitude(longitude);
        asset.setLatitude(latitude);

        AssetLocation latestLocation = AssetLocation.builder()
                .asset(asset)
                .longitude(asset.getLongitude())
                .latitude(asset.getLatitude())
                .timestamp(LocalDateTime.now())
                .build();

        asset.getAssetLocations().add(latestLocation);
    }


}