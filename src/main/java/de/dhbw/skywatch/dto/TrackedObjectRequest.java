package de.dhbw.skywatch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

        // only checks the format YYYY-MM-DD, not if the date really exists
        @NotBlank(message = "closeApproachDate must not be blank")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "closeApproachDate must be in format YYYY-MM-DD")
        String closeApproachDate,

        @PositiveOrZero(message = "missDistanceKm must be zero or positive")
        double missDistanceKm
) {}
