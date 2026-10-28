package de.dhbw.skywatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// An asteroid or comet we keep an eye on.
// @Entity -> Hibernate creates a TRACKED_OBJECT table for it
@Entity
public class TrackedObject {

    // primary key, the database hands out the ids (auto increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double estimatedDiameterKm;
    private boolean potentiallyHazardous;
    private String closeApproachDate; // ISO date like "2029-04-13"
    private double missDistanceKm;    // how close it gets to earth

    // JPA needs a no-args constructor, protected is enough
    protected TrackedObject() {}

    public TrackedObject(Long id, String name, double estimatedDiameterKm,
                         boolean potentiallyHazardous, String closeApproachDate,
                         double missDistanceKm) {
        this.id = id;
        this.name = name;
        this.estimatedDiameterKm = estimatedDiameterKm;
        this.potentiallyHazardous = potentiallyHazardous;
        this.closeApproachDate = closeApproachDate;
        this.missDistanceKm = missDistanceKm;
    }

    // getters + setters (Jackson uses the getters for the JSON output)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getEstimatedDiameterKm() { return estimatedDiameterKm; }
    public void setEstimatedDiameterKm(double estimatedDiameterKm) {
        this.estimatedDiameterKm = estimatedDiameterKm;
    }

    public boolean isPotentiallyHazardous() { return potentiallyHazardous; }
    public void setPotentiallyHazardous(boolean potentiallyHazardous) {
        this.potentiallyHazardous = potentiallyHazardous;
    }

    public String getCloseApproachDate() { return closeApproachDate; }
    public void setCloseApproachDate(String closeApproachDate) {
        this.closeApproachDate = closeApproachDate;
    }

    public double getMissDistanceKm() { return missDistanceKm; }
    public void setMissDistanceKm(double missDistanceKm) {
        this.missDistanceKm = missDistanceKm;
    }
}
