package de.dhbw.skywatch.controller;

import de.dhbw.skywatch.dto.TrackedObjectRequest;
import de.dhbw.skywatch.dto.TrackedObjectResponse;
import de.dhbw.skywatch.mapper.TrackedObjectMapper;
import de.dhbw.skywatch.model.TrackedObject;
import de.dhbw.skywatch.service.TrackedObjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

// REST endpoints for tracked asteroids, all under /api/tracked-objects
@RestController
@RequestMapping("/api/tracked-objects")
public class TrackedObjectController {

    private final TrackedObjectService service;

    // constructor injection - Spring passes in the service bean
    public TrackedObjectController(TrackedObjectService service) {
        this.service = service;
    }

    // GET /api/tracked-objects
    // optional filter: ?hazardous=true or ?hazardous=false
    @GetMapping
    public List<TrackedObjectResponse> getAll(
            @RequestParam(required = false) Boolean hazardous) {
        // no param -> everything, otherwise let the database filter
        List<TrackedObject> objects = (hazardous == null)
                ? service.findAll()
                : service.findByHazardous(hazardous);
        return objects.stream()
                .map(TrackedObjectMapper::toResponse)
                .toList();
    }

    // GET /api/tracked-objects/{id} -> 200, unknown id -> 404 via exception handler
    @GetMapping("/{id}")
    public TrackedObjectResponse getById(@PathVariable Long id) {
        return TrackedObjectMapper.toResponse(service.getOrThrow(id));
    }

    // POST /api/tracked-objects -> 201 Created + Location header
    // @Valid checks the request, invalid input -> 400
    @PostMapping
    public ResponseEntity<TrackedObjectResponse> create(@Valid @RequestBody TrackedObjectRequest request) {
        TrackedObject created = service.create(TrackedObjectMapper.toEntity(request));
        TrackedObjectResponse response = TrackedObjectMapper.toResponse(created);
        // Location points to the new resource, e.g. /api/tracked-objects/4
        return ResponseEntity
                .created(URI.create("/api/tracked-objects/" + created.getId()))
                .body(response);
    }

    // PUT /api/tracked-objects/{id} -> replaces the whole object, 200 or 404
    @PutMapping("/{id}")
    public TrackedObjectResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TrackedObjectRequest request) {
        TrackedObject updated = service.update(id, TrackedObjectMapper.toEntity(request));
        return TrackedObjectMapper.toResponse(updated);
    }

    // PATCH /api/tracked-objects/{id}/hazardous -> toggles the hazardous flag, 200 or 404
    @PatchMapping("/{id}/hazardous")
    public TrackedObjectResponse toggleHazardous(@PathVariable Long id) {
        return TrackedObjectMapper.toResponse(service.toggleHazardous(id));
    }

    // DELETE /api/tracked-objects/{id} -> 204 No Content, or 404 if it's already gone
    // @ResponseStatus sets the default status, so no ResponseEntity needed
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // GET /api/tracked-objects/search?name=apo
    @GetMapping("/search")
    public List<TrackedObjectResponse> search(@RequestParam String name) {
        return service.searchByName(name).stream()
                .map(TrackedObjectMapper::toResponse)
                .toList();
    }

    // GET /api/tracked-objects/large -> diameter > 0.3 km
    @GetMapping("/large")
    public List<TrackedObjectResponse> getLarge() {
        return service.findLarge().stream()
                .map(TrackedObjectMapper::toResponse)
                .toList();
    }

    // GET /api/tracked-objects/count -> just the number, e.g. 3
    // (fixed paths like /count win over /{id}, so no conflict here)
    @GetMapping("/count")
    public long count() {
        return service.count();
    }
}
