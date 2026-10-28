package de.dhbw.skywatch.dto.nasa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

// Top level of the NASA feed response.
// Field names are snake_case on purpose so they match the JSON 1:1 (no @JsonProperty needed)
// ignoreUnknown: we only map what we need, new NASA fields won't break us
@JsonIgnoreProperties(ignoreUnknown = true)
public record NeoFeedResponse(
        int element_count,
        // key = date like "2026-10-28", value = asteroids for that day
        Map<String, List<NeoObject>> near_earth_objects
) {}
