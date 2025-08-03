package com.nutrifit.foodlog.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record FoodLogResponse(

        @NotNull(message = "ID cannot be null")
        UUID id,
        @NotNull(message = "User ID cannot be null")
        UUID userId,
        @NotNull(message = "Date cannot be null")
        LocalDate date
) {
}
