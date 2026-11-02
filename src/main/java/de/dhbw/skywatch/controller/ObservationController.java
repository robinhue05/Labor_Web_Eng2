package de.dhbw.skywatch.controller;

import de.dhbw.skywatch.dto.ObservationRequest;
import de.dhbw.skywatch.dto.ObservationResponse;
import de.dhbw.skywatch.mapper.ObservationMapper;
import de.dhbw.skywatch.model.ObservationSession;
import de.dhbw.skywatch.model.TrackedObject;
import de.dhbw.skywatch.repository.ObservationSessionRepository;
import de.dhbw.skywatch.repository.TrackedObjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

// Observations always belong to a tracked object, so the URL is nested
@RestController
@RequestMapping("/api/tracked-objects/{trackedObjectId}/observations")
public class ObservationController {

    private final TrackedObjectRepository trackedObjectRepository;
    private final ObservationSessionRepository observationRepository;

    public ObservationController(TrackedObjectRepository trackedObjectRepository,
                                 ObservationSessionRepository observationRepository) {
        this.trackedObjectRepository = trackedObjectRepository;
        this.observationRepository = observationRepository;
    }

    // GET .../observations -> all observations of that object, 404 if the object doesn't exist
    @GetMapping
    public ResponseEntity<List<ObservationResponse>> getAll(@PathVariable Long trackedObjectId) {
        if (!trackedObjectRepository.existsById(trackedObjectId)) {
            return ResponseEntity.notFound().build();
        }
        List<ObservationResponse> observations = observationRepository
                .findByTrackedObjectId(trackedObjectId).stream()
                .map(ObservationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(observations);
    }

    // POST .../observations -> 201 Created, or 404 if the object doesn't exist
    @PostMapping
    public ResponseEntity<ObservationResponse> create(
            @PathVariable Long trackedObjectId,
            @RequestBody ObservationRequest request) {
        Optional<TrackedObject> parent = trackedObjectRepository.findById(trackedObjectId);
        if (parent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ObservationSession observation = new ObservationSession(
                request.observerName(),
                request.observedAt(),
                request.location(),
                request.notes(),
                parent.get()
        );
        ObservationSession saved = observationRepository.save(observation);

        return ResponseEntity
                .created(URI.create("/api/tracked-objects/" + trackedObjectId
                        + "/observations/" + saved.getId()))
                .body(ObservationMapper.toResponse(saved));
    }
}
