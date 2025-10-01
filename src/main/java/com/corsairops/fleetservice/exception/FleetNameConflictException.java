package com.corsairops.fleetservice.exception;

import com.corsairops.shared.exception.HttpResponseException;
import org.springframework.http.HttpStatus;

public class FleetNameConflictException extends HttpResponseException {
    public FleetNameConflictException(String message, HttpStatus status) {
        super(message, status);
    }
}