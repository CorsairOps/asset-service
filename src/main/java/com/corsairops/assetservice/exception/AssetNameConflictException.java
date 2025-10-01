package com.corsairops.assetservice.exception;

import com.corsairops.shared.exception.HttpResponseException;
import org.springframework.http.HttpStatus;

public class AssetNameConflictException extends HttpResponseException {
    public AssetNameConflictException(String message, HttpStatus status) {
        super(message, status);
    }
}