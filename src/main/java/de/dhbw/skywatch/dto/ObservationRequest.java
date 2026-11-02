package de.dhbw.skywatch.dto;

import java.time.LocalDateTime;

// Body for POST .../observations, observedAt as ISO string e.g. "2026-10-28T21:30:00"
public record ObservationRequest(
        String observerName,
        LocalDateTime observedAt,
        String location,
        String notes
) {}
