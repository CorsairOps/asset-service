package com.corsairops.fleetservice.controller;

import com.corsairops.fleetservice.dto.FleetRequest;
import com.corsairops.fleetservice.dto.FleetResponse;
import com.corsairops.fleetservice.service.FleetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleets")
@RequiredArgsConstructor
public class FleetController {

    private final FleetService fleetService;

    @PostMapping
    public FleetResponse createFleet(@RequestBody @Valid FleetRequest fleetRequest) {
        return FleetResponse.from(fleetService.createFleet(fleetRequest));
    }

    @GetMapping
    public List<FleetResponse> getAllFleets() {
        return fleetService.getAllFleets().stream()
                .map(FleetResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public FleetResponse getFleetById(@PathVariable("id") Long id) {
        return FleetResponse.from(fleetService.getFleetById(id));
    }

    @PutMapping("/{id}")
    public FleetResponse updateFleet(@PathVariable("id") Long id, @RequestBody @Valid FleetRequest fleetRequest) {
        return FleetResponse.from(fleetService.updateFleet(id, fleetRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteFleet(@PathVariable("id") Long id) {
        fleetService.deleteFleet(id);
    }

}