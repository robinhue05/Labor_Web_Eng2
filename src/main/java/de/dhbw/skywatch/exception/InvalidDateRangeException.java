package de.dhbw.skywatch.exception;

// Thrown for a bad start/end combination (end before start, more than 7 days) -> 400
public class InvalidDateRangeException extends RuntimeException {

    public InvalidDateRangeException(String message) {
        super(message);
    }
}
