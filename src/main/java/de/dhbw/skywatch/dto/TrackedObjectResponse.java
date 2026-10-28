package de.dhbw.skywatch.dto;

// What the API returns to the client (includes the id)
public record TrackedObjectResponse(
        Long id,
        String name,
        double estimatedDiameterKm,
        boolean potentiallyHazardous,
        String closeApproachDate,
        double missDistanceKm
) {}
