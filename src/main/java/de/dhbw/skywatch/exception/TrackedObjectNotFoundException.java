package de.dhbw.skywatch.exception;

// Thrown when there's no tracked object with the given id -> ends up as 404
public class TrackedObjectNotFoundException extends RuntimeException {

    public TrackedObjectNotFoundException(Long id) {
        super("TrackedObject with id " + id + " not found");
    }
}
