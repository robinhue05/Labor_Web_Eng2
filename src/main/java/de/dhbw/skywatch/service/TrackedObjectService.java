package de.dhbw.skywatch.service;

import de.dhbw.skywatch.exception.TrackedObjectNotFoundException;
import de.dhbw.skywatch.model.TrackedObject;
import de.dhbw.skywatch.repository.TrackedObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Business logic for tracked objects, data now lives in the database
@Service
public class TrackedObjectService {

    // everything above this diameter counts as "large"
    private static final double LARGE_DIAMETER_KM = 0.3;

    private final TrackedObjectRepository repository;

    public TrackedObjectService(TrackedObjectRepository repository) {
        this.repository = repository;
    }

    public List<TrackedObject> findAll() {
        return repository.findAll();
    }

    // true -> only hazardous ones, false -> only harmless ones
    public List<TrackedObject> findByHazardous(boolean hazardous) {
        return repository.findByPotentiallyHazardous(hazardous);
    }

    // case-insensitive name search, "apo" finds "99942 Apophis"
    public List<TrackedObject> searchByName(String namePart) {
        return repository.findByNameContainingIgnoreCase(namePart);
    }

    // all objects with diameter > 0.3 km
    public List<TrackedObject> findLarge() {
        return repository.findLargerThan(LARGE_DIAMETER_KM);
    }

    // returns the object or throws -> the exception handler turns that into a 404
    public TrackedObject getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TrackedObjectNotFoundException(id));
    }

    public long count() {
        return repository.count();
    }

    // save() does an INSERT here because the id is still null
    public TrackedObject create(TrackedObject entity) {
        return repository.save(entity);
    }

    // copy all fields onto the existing row
    public TrackedObject update(Long id, TrackedObject updated) {
        TrackedObject existing = getOrThrow(id);
        existing.setName(updated.getName());
        existing.setEstimatedDiameterKm(updated.getEstimatedDiameterKm());
        existing.setPotentiallyHazardous(updated.isPotentiallyHazardous());
        existing.setCloseApproachDate(updated.getCloseApproachDate());
        existing.setMissDistanceKm(updated.getMissDistanceKm());
        return repository.save(existing);
    }

    // flips potentiallyHazardous (true <-> false)
    public TrackedObject toggleHazardous(Long id) {
        TrackedObject obj = getOrThrow(id);
        obj.setPotentiallyHazardous(!obj.isPotentiallyHazardous());
        return repository.save(obj);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TrackedObjectNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
