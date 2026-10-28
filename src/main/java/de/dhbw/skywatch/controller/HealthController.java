package de.dhbw.skywatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Simple endpoint to check if the API is up
@RestController
public class HealthController {

    // GET /api/health -> {"status":"UP", ...}
    @GetMapping("/api/health")
    public Map<String, String> health() {
        // Spring turns the map into JSON automatically
        return Map.of(
                "status", "UP",
                "application", "SkyWatch",
                "version", "0.1.0"
        );
    }
}
