package de.dhbw.skywatch.controller;

import de.dhbw.skywatch.model.TrackedObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// REST endpoints for tracked asteroids, all under /api/tracked-objects
@RestController
@RequestMapping("/api/tracked-objects")
public class TrackedObjectController {

    // hardcoded for now, gets replaced by real data later (session 5/6)
    private final List<TrackedObject> trackedObjects = List.of(
            new TrackedObject(1L, "2024 YR4", 0.07, false,
                    "2032-12-22", 3_200_000.0),
            new TrackedObject(2L, "99942 Apophis", 0.37, true,
                    "2029-04-13", 31_000.0),
            new TrackedObject(3L, "Bennu", 0.49, true,
                    "2182-09-25", 750_000.0)
    );

    // GET /api/tracked-objects
    @GetMapping
    public List<TrackedObject> getAll() {
        return trackedObjects;
    }

    // GET /api/tracked-objects/{id}
    @GetMapping("/{id}")
    public TrackedObject getById(@PathVariable Long id) {
        return trackedObjects.stream()
                .filter(obj -> obj.getId().equals(id))
                .findFirst()
                .orElse(null); // TODO: proper 404 via exception handling (session 7)
    }

    // GET /api/tracked-objects/hazardous -> only the dangerous ones
    @GetMapping("/hazardous")
    public List<TrackedObject> getHazardous() {
        return trackedObjects.stream()
                .filter(TrackedObject::isPotentiallyHazardous)
                .toList();
    }
}
