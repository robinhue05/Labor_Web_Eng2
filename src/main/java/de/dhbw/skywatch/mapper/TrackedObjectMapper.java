package de.dhbw.skywatch.mapper;

import de.dhbw.skywatch.dto.TrackedObjectRequest;
import de.dhbw.skywatch.dto.TrackedObjectResponse;
import de.dhbw.skywatch.model.TrackedObject;

// Converts between DTOs (API side) and the TrackedObject model (inside the app)
public final class TrackedObjectMapper {

    // only static helpers, no instances needed
    private TrackedObjectMapper() {}

    // request -> model, id is null for new objects
    public static TrackedObject toEntity(Long id, TrackedObjectRequest request) {
        return new TrackedObject(
                id,
                request.name(),
                request.estimatedDiameterKm(),
                request.potentiallyHazardous(),
                request.closeApproachDate(),
                request.missDistanceKm()
        );
    }

    // model -> response
    public static TrackedObjectResponse toResponse(TrackedObject entity) {
        return new TrackedObjectResponse(
                entity.getId(),
                entity.getName(),
                entity.getEstimatedDiameterKm(),
                entity.isPotentiallyHazardous(),
                entity.getCloseApproachDate(),
                entity.getMissDistanceKm()
        );
    }
}
