package com.corsairops.fleetservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "fleet_assets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FleetAsset {

    @Id
    @JoinColumn(name = "asset_id")
    @OneToOne
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "fleet_id", nullable = false)
    private Fleet fleet;

    private LocalDateTime assignedAt;
}