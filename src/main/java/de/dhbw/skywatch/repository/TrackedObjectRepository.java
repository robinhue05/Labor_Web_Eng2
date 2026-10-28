package de.dhbw.skywatch.repository;

import de.dhbw.skywatch.model.TrackedObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data generates the implementation at runtime.
// findAll, findById, save, deleteById, count ... come for free from JpaRepository
public interface TrackedObjectRepository extends JpaRepository<TrackedObject, Long> {

    // derived query: WHERE potentially_hazardous = ?
    List<TrackedObject> findByPotentiallyHazardous(boolean potentiallyHazardous);

    // derived query: WHERE LOWER(name) LIKE LOWER('%namePart%')
    List<TrackedObject> findByNameContainingIgnoreCase(String namePart);
}
