package de.dhbw.skywatch.config;

import de.dhbw.skywatch.model.TrackedObject;
import de.dhbw.skywatch.repository.TrackedObjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Fills the database with some example asteroids on startup
@Configuration
public class DataSeeder {

    // CommandLineRunner runs once after the app has started
    @Bean
    CommandLineRunner seedTrackedObjects(TrackedObjectRepository repository) {
        return args -> {
            // don't seed twice if there's already data
            if (repository.count() > 0) {
                return;
            }
            repository.save(new TrackedObject("2024 YR4", 0.07, false,
                    "2032-12-22", 3_200_000.0));
            repository.save(new TrackedObject("99942 Apophis", 0.37, true,
                    "2029-04-13", 31_000.0));
            repository.save(new TrackedObject("Bennu", 0.49, true,
                    "2182-09-25", 750_000.0));
        };
    }
}
