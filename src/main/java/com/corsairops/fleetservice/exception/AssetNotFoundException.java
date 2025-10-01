package com.corsairops.fleetservice.exception;

import com.corsairops.shared.exception.HttpResponseException;
import org.springframework.http.HttpStatus;

public class AssetNotFoundException extends HttpResponseException {
    public AssetNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}