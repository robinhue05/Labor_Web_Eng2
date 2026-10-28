package de.dhbw.skywatch.exception;

// Thrown when an observation doesn't exist (or belongs to another object) -> 404
public class ObservationNotFoundException extends RuntimeException {

    public ObservationNotFoundException(Long id) {
        super("Observation with id " + id + " not found");
    }
}
