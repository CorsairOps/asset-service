package com.corsairops.fleetservice.service;

import com.corsairops.fleetservice.dto.FleetRequest;
import com.corsairops.fleetservice.exception.FleetNameConflictException;
import com.corsairops.fleetservice.exception.FleetNotFoundException;
import com.corsairops.fleetservice.model.Fleet;
import com.corsairops.fleetservice.repository.FleetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FleetService {
    private final FleetRepository fleetRepository;

    @Transactional(readOnly = true)
    public List<Fleet> getAllFleets() {
        return fleetRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Fleet getFleetById(Long id) {
        return fleetRepository.findById(id)
                .orElseThrow(() -> new FleetNotFoundException("Fleet not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Fleet createFleet(FleetRequest fleetRequest) {
        validateFleetName(fleetRequest.name());
        Fleet fleet = Fleet.builder()
                .name(fleetRequest.name())
                .description(fleetRequest.description())
                .build();

        return fleetRepository.save(fleet);
    }

    @Transactional
    public Fleet updateFleet(Long id, FleetRequest fleetRequest) {
        Fleet existingFleet = getFleetById(id);
        validateFleetName(fleetRequest.name(), id);

        existingFleet.setName(fleetRequest.name());
        existingFleet.setDescription(fleetRequest.description());

        return fleetRepository.save(existingFleet);
    }

    @Transactional
    public void deleteFleet(Long id) {
        if (!fleetRepository.existsById(id)) {
            throw new FleetNotFoundException("Fleet not found", HttpStatus.NOT_FOUND);
        }
        fleetRepository.deleteById(id);
    }

    private void validateFleetName(String name) {
        if (fleetRepository.existsByName(name)) {
            throw new FleetNameConflictException("Fleet name already exists", HttpStatus.CONFLICT);
        }
    }

    private void validateFleetName(String name, Long id) {
        if (fleetRepository.existsByNameAndIdNot(name, id)) {
            throw new FleetNameConflictException("Fleet name already exists", HttpStatus.CONFLICT);
        }
    }
}