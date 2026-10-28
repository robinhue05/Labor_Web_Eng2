package de.dhbw.skywatch.dto;

// What the client sends for POST/PUT - no id, the server picks that
public record TrackedObjectRequest(
        String name,
        double estimatedDiameterKm,
        boolean potentiallyHazardous,
        String closeApproachDate,
        double missDistanceKm
) {}
