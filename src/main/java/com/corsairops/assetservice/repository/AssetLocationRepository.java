package com.corsairops.assetservice.repository;

import com.corsairops.assetservice.model.AssetLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssetLocationRepository extends JpaRepository<AssetLocation, Long> {

    @Query("SELECT al FROM AssetLocation al WHERE al.asset.id = :assetId ORDER BY al.timestamp DESC LIMIT :limit")
    List<AssetLocation> findTopNByAssetIdOrderByTimestampDesc(@Param("assetId") UUID assetId, @Param("limit") int limit);
}