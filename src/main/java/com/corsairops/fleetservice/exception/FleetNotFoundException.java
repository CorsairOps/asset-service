package com.corsairops.fleetservice.exception;

import com.corsairops.shared.exception.HttpResponseException;
import org.springframework.http.HttpStatus;

public class FleetNotFoundException extends HttpResponseException {
    public FleetNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}