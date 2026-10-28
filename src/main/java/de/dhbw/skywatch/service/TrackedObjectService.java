package de.dhbw.skywatch.service;

import de.dhbw.skywatch.model.TrackedObject;
import de.dhbw.skywatch.repository.TrackedObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Business logic for tracked objects, data now lives in the database
@Service
public class TrackedObjectService {

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

    public Optional<TrackedObject> findById(Long id) {
        return repository.findById(id);
    }

    public long count() {
        return repository.count();
    }

    // save() does an INSERT here because the id is still null
    public TrackedObject create(TrackedObject entity) {
        return repository.save(entity);
    }

    // copy all fields onto the existing row, empty if the id doesn't exist
    public Optional<TrackedObject> update(Long id, TrackedObject updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEstimatedDiameterKm(updated.getEstimatedDiameterKm());
            existing.setPotentiallyHazardous(updated.isPotentiallyHazardous());
            existing.setCloseApproachDate(updated.getCloseApproachDate());
            existing.setMissDistanceKm(updated.getMissDistanceKm());
            return repository.save(existing);
        });
    }

    // flips potentiallyHazardous (true <-> false), empty if the id doesn't exist
    public Optional<TrackedObject> toggleHazardous(Long id) {
        return repository.findById(id).map(obj -> {
            obj.setPotentiallyHazardous(!obj.isPotentiallyHazardous());
            return repository.save(obj);
        });
    }

    // true if something was actually deleted
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
