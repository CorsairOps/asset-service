package com.corsairops.fleetservice.service;

import com.corsairops.fleetservice.dto.AssetRequest;
import com.corsairops.fleetservice.exception.AssetNameConflictException;
import com.corsairops.fleetservice.exception.AssetNotFoundException;
import com.corsairops.fleetservice.model.Asset;
import com.corsairops.fleetservice.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional
    public Asset createAsset(AssetRequest assetRequest) {
        validateUniqueName(assetRequest.name());

        Asset asset = Asset.builder()
                .name(assetRequest.name())
                .type(assetRequest.type())
                .status(assetRequest.status())
                .build();

        return assetRepository.save(asset);
    }

    @Transactional
    public Asset updateAsset(UUID id, AssetRequest assetRequest) {
        Asset existingAsset = getAssetById(id);
        validateUniqueName(assetRequest.name(), id);

        existingAsset.setName(assetRequest.name());
        existingAsset.setType(assetRequest.type());
        existingAsset.setStatus(assetRequest.status());

        return assetRepository.save(existingAsset);
    }

    @Transactional
    public void deleteAsset(UUID id) {
        if (!assetRepository.existsById(id)) {
            throw new AssetNotFoundException("Asset not found", HttpStatus.NOT_FOUND);
        }
        assetRepository.deleteById(id);
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
}