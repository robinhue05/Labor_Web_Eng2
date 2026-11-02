package de.dhbw.skywatch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

// What the client sends for POST/PUT - no id, the server picks that.
// The annotations get checked when the controller uses @Valid
public record TrackedObjectRequest(

        @NotBlank(message = "name must not be blank")
        @Size(max = 100, message = "name must be at most 100 characters")
        String name,

        @Positive(message = "estimatedDiameterKm must be positive")
        double estimatedDiameterKm,

        boolean potentiallyHazardous,

        @NotBlank(message = "closeApproachDate must not be blank")
        String closeApproachDate,

        @PositiveOrZero(message = "missDistanceKm must be zero or positive")
        double missDistanceKm
) {}
