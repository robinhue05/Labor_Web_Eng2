package de.dhbw.skywatch.controller;

import de.dhbw.skywatch.dto.ObservationRequest;
import de.dhbw.skywatch.dto.ObservationResponse;
import de.dhbw.skywatch.mapper.ObservationMapper;
import de.dhbw.skywatch.model.ObservationSession;
import de.dhbw.skywatch.service.ObservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

// Observations always belong to a tracked object, so the URL is nested.
// Unknown ids end up as 404 via the GlobalExceptionHandler
@RestController
@RequestMapping("/api/tracked-objects/{trackedObjectId}/observations")
public class ObservationController {

    private final ObservationService service;

    public ObservationController(ObservationService service) {
        this.service = service;
    }

    // GET .../observations -> all observations of that object
    @GetMapping
    public List<ObservationResponse> getAll(@PathVariable Long trackedObjectId) {
        return service.findAllFor(trackedObjectId).stream()
                .map(ObservationMapper::toResponse)
                .toList();
    }

    // GET .../observations/{observationId} -> a single one
    @GetMapping("/{observationId}")
    public ObservationResponse getById(@PathVariable Long trackedObjectId,
                                       @PathVariable Long observationId) {
        return ObservationMapper.toResponse(service.getOrThrow(trackedObjectId, observationId));
    }

    // POST .../observations -> 201 Created, invalid body -> 400
    @PostMapping
    public ResponseEntity<ObservationResponse> create(
            @PathVariable Long trackedObjectId,
            @Valid @RequestBody ObservationRequest request) {
        ObservationSession saved = service.create(
                trackedObjectId,
                request.observerName(),
                request.observedAt(),
                request.location(),
                request.notes()
        );

        return ResponseEntity
                .created(URI.create("/api/tracked-objects/" + trackedObjectId
                        + "/observations/" + saved.getId()))
                .body(ObservationMapper.toResponse(saved));
    }
}
