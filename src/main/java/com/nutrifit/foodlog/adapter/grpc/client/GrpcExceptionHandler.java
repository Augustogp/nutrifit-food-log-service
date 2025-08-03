package com.nutrifit.foodlog.adapter.grpc.client;

import com.nutrifit.foodlog.exception.ResourceNotFoundException;
import com.nutrifit.foodlog.exception.ExternalServiceException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GrpcExceptionHandler {

    public <T> T handle(String context, UUID id, GrpcCall<T> grpcCall) {
        try {
            return grpcCall.run();
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new ResourceNotFoundException("Resource not found for " + context + " with id: " + id);
            } else if (e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT) {
                throw new IllegalArgumentException("Invalid argument provided for " + context + " with id: " + id, e);
            } else {
                throw new ExternalServiceException("gRPC error in " + context + ": " + e.getStatus(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while processing the gRPC call", e);
        }
    }


    @FunctionalInterface
    public interface GrpcCall<T> {
        T run() throws StatusRuntimeException;
    }
}
