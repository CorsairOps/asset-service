package com.corsairops.fleetservice.repository;

import com.corsairops.fleetservice.model.Fleet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FleetRepository extends JpaRepository<Fleet, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}