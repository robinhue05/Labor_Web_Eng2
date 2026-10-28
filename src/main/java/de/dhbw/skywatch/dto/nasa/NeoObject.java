package de.dhbw.skywatch.dto.nasa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// One asteroid as NASA sends it (only the fields we care about)
@JsonIgnoreProperties(ignoreUnknown = true)
public record NeoObject(
        String id,
        String name,
        EstimatedDiameter estimated_diameter,
        boolean is_potentially_hazardous_asteroid,
        List<CloseApproachData> close_approach_data
) {

    // nested JSON objects -> nested records

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record EstimatedDiameter(Kilometers kilometers) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Kilometers(
            double estimated_diameter_min,
            double estimated_diameter_max
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CloseApproachData(
            String close_approach_date,
            MissDistance miss_distance
    ) {}

    // NASA sends the distance as a string, e.g. "5421329.3"
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MissDistance(String kilometers) {}
}
