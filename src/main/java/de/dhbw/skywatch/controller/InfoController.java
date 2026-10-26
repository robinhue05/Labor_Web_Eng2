package de.dhbw.skywatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Basic info about the API itself
@RestController
public class InfoController {

    // GET /api/info -> {"name":"SkyWatch API","version":"0.1.0"}
    @GetMapping("/api/info")
    public Map<String, String> info() {
        return Map.of(
                "name", "SkyWatch API",
                "version", "0.1.0"
        );
    }
}
