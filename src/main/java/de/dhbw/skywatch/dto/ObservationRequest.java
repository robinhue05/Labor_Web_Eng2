package de.dhbw.skywatch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

// Body for POST .../observations, observedAt as ISO string e.g. "2026-10-28T21:30:00"
public record ObservationRequest(

        @NotBlank(message = "observerName must not be blank")
        String observerName,

        // @Past alone lets null through, so @NotNull too
        @NotNull(message = "observedAt is required")
        @Past(message = "observedAt must be in the past")
        LocalDateTime observedAt,

        String location,

        // optional, but not a novel
        @Size(max = 500, message = "notes must be at most 500 characters")
        String notes
) {}
