package de.dhbw.skywatch.mapper;

import de.dhbw.skywatch.dto.nasa.NeoObject;
import de.dhbw.skywatch.model.TrackedObject;

// Turns a NASA asteroid into our own TrackedObject (not saved yet)
public final class NeoObjectMapper {

    private NeoObjectMapper() {}

    public static TrackedObject toEntity(NeoObject source) {
        return new TrackedObject(
                source.name(),
                averageDiameterKm(source),
                source.is_potentially_hazardous_asteroid(),
                approachDate(source),
                missDistanceKm(source)
        );
    }

    // NASA gives min + max, we store the middle
    private static double averageDiameterKm(NeoObject source) {
        if (source.estimated_diameter() == null || source.estimated_diameter().kilometers() == null) {
            return 0.0;
        }
        NeoObject.Kilometers km = source.estimated_diameter().kilometers();
        return (km.estimated_diameter_min() + km.estimated_diameter_max()) / 2.0;
    }

    // we only use the first close approach, null-safe in case NASA sends nothing
    private static NeoObject.CloseApproachData firstApproach(NeoObject source) {
        if (source.close_approach_data() == null || source.close_approach_data().isEmpty()) {
            return null;
        }
        return source.close_approach_data().get(0);
    }

    private static String approachDate(NeoObject source) {
        NeoObject.CloseApproachData approach = firstApproach(source);
        return approach == null ? null : approach.close_approach_date();
    }

    private static double missDistanceKm(NeoObject source) {
        NeoObject.CloseApproachData approach = firstApproach(source);
        if (approach == null || approach.miss_distance() == null
                || approach.miss_distance().kilometers() == null) {
            return 0.0;
        }
        // comes as a string from NASA
        return Double.parseDouble(approach.miss_distance().kilometers());
    }
}
