package de.dhbw.skywatch.mapper;

import de.dhbw.skywatch.dto.ObservationResponse;
import de.dhbw.skywatch.model.ObservationSession;

// Entity -> DTO for observations
public final class ObservationMapper {

    private ObservationMapper() {}

    public static ObservationResponse toResponse(ObservationSession entity) {
        return new ObservationResponse(
                entity.getId(),
                entity.getObserverName(),
                entity.getObservedAt(),
                entity.getLocation(),
                entity.getNotes(),
                entity.getTrackedObject().getId()
        );
    }
}
