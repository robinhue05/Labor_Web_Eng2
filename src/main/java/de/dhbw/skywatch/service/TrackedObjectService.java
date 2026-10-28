package de.dhbw.skywatch.service;

import de.dhbw.skywatch.model.TrackedObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Business logic for tracked objects.
// Data lives in memory for now, gets replaced by a JPA repository later.
@Service
public class TrackedObjectService {

    // thread-safe because Spring handles requests in parallel
    private final ConcurrentHashMap<Long, TrackedObject> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TrackedObjectService() {
        // some seed data so we have something to play with
        create(new TrackedObject(null, "2024 YR4", 0.07, false,
                "2032-12-22", 3_200_000.0));
        create(new TrackedObject(null, "99942 Apophis", 0.37, true,
                "2029-04-13", 31_000.0));
        create(new TrackedObject(null, "Bennu", 0.49, true,
                "2182-09-25", 750_000.0));
    }

    public List<TrackedObject> findAll() {
        // copy so callers can't mess with our store
        return List.copyOf(store.values());
    }

    public Optional<TrackedObject> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // gives the object a new id and saves it
    public TrackedObject create(TrackedObject entity) {
        Long newId = idGenerator.getAndIncrement();
        entity.setId(newId);
        store.put(newId, entity);
        return entity;
    }

    // replaces the whole object, empty if the id doesn't exist
    public Optional<TrackedObject> update(Long id, TrackedObject entity) {
        if (!store.containsKey(id)) {
            return Optional.empty();
        }
        entity.setId(id);
        store.put(id, entity);
        return Optional.of(entity);
    }

    // true if something was actually deleted
    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
