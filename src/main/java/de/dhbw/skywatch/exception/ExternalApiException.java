package de.dhbw.skywatch.exception;

// Something went wrong while calling another service (NASA) -> 502 Bad Gateway
public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String message) {
        super(message);
    }

    // keeps the original exception for the logs
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
