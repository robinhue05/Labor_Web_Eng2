package de.dhbw.skywatch.service;

import de.dhbw.skywatch.exception.ObservationNotFoundException;
import de.dhbw.skywatch.model.ObservationSession;
import de.dhbw.skywatch.model.TrackedObject;
import de.dhbw.skywatch.repository.ObservationSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Business logic for observations, always in the context of one tracked object
@Service
public class ObservationService {

    private final ObservationSessionRepository repository;
    private final TrackedObjectService trackedObjectService;

    public ObservationService(ObservationSessionRepository repository,
                              TrackedObjectService trackedObjectService) {
        this.repository = repository;
        this.trackedObjectService = trackedObjectService;
    }

    // all observations of one object, 404 if the object doesn't exist
    public List<ObservationSession> findAllFor(Long trackedObjectId) {
        trackedObjectService.getOrThrow(trackedObjectId);
        return repository.findByTrackedObjectId(trackedObjectId);
    }

    // one observation, has to belong to that object
    public ObservationSession getOrThrow(Long trackedObjectId, Long observationId) {
        trackedObjectService.getOrThrow(trackedObjectId);
        return repository.findByIdAndTrackedObjectId(observationId, trackedObjectId)
                .orElseThrow(() -> new ObservationNotFoundException(observationId));
    }

    public ObservationSession create(Long trackedObjectId, String observerName,
                                     LocalDateTime observedAt, String location, String notes) {
        TrackedObject parent = trackedObjectService.getOrThrow(trackedObjectId);
        ObservationSession observation =
                new ObservationSession(observerName, observedAt, location, notes, parent);
        return repository.save(observation);
    }
}
