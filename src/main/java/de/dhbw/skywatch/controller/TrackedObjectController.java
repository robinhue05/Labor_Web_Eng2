package de.dhbw.skywatch.controller;

import de.dhbw.skywatch.dto.TrackedObjectRequest;
import de.dhbw.skywatch.dto.TrackedObjectResponse;
import de.dhbw.skywatch.mapper.TrackedObjectMapper;
import de.dhbw.skywatch.model.TrackedObject;
import de.dhbw.skywatch.service.TrackedObjectService;
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
    @GetMapping
    public List<TrackedObjectResponse> getAll() {
        return service.findAll().stream()
                .map(TrackedObjectMapper::toResponse)
                .toList();
    }

    // GET /api/tracked-objects/{id} -> 200 or 404
    @GetMapping("/{id}")
    public ResponseEntity<TrackedObjectResponse> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(TrackedObjectMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/tracked-objects -> 201 Created + Location header
    @PostMapping
    public ResponseEntity<TrackedObjectResponse> create(@RequestBody TrackedObjectRequest request) {
        TrackedObject created = service.create(TrackedObjectMapper.toEntity(null, request));
        TrackedObjectResponse response = TrackedObjectMapper.toResponse(created);
        // Location points to the new resource, e.g. /api/tracked-objects/4
        return ResponseEntity
                .created(URI.create("/api/tracked-objects/" + created.getId()))
                .body(response);
    }

    // PUT /api/tracked-objects/{id} -> replaces the whole object, 200 or 404
    @PutMapping("/{id}")
    public ResponseEntity<TrackedObjectResponse> update(
            @PathVariable Long id,
            @RequestBody TrackedObjectRequest request) {
        return service.update(id, TrackedObjectMapper.toEntity(id, request))
                .map(TrackedObjectMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tracked-objects/{id} -> 204 No Content, or 404 if it's already gone
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/tracked-objects/hazardous -> only the dangerous ones
    @GetMapping("/hazardous")
    public List<TrackedObjectResponse> getHazardous() {
        return service.findAll().stream()
                .filter(TrackedObject::isPotentiallyHazardous)
                .map(TrackedObjectMapper::toResponse)
                .toList();
    }

    // GET /api/tracked-objects/count -> just the number, e.g. 3
    // (fixed paths like /count win over /{id}, so no conflict here)
    @GetMapping("/count")
    public int count() {
        return service.findAll().size();
    }
}
