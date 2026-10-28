package de.dhbw.skywatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

// Turns on @Retryable support for the whole app
@Configuration
@EnableRetry
public class RetryConfig {
}
