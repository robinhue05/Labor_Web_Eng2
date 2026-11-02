package de.dhbw.skywatch.dto;

import java.time.LocalDateTime;

// What we send back for an observation.
// Only the parent id, not the whole entity -> avoids lazy loading and endless JSON loops
public record ObservationResponse(
        Long id,
        String observerName,
        LocalDateTime observedAt,
        String location,
        String notes,
        Long trackedObjectId
) {}
