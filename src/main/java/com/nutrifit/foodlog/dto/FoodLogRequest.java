package com.nutrifit.foodlog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.UUID;

public record FoodLogRequest(

        @NotNull(message = "User ID cannot be null")
        UUID userId,

        @NotNull(message = "Date cannot be null")
        LocalDate date
) {
}
