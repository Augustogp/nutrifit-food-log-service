package com.nutrifit.foodlog.exception;

import io.grpc.StatusRuntimeException;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }

    public ExternalServiceException(String s, StatusRuntimeException e) {
    }
}
