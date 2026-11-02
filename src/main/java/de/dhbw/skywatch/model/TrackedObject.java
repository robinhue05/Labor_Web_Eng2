package de.dhbw.skywatch.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

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

    // all observations of this object
    // mappedBy: the FK lives in ObservationSession.trackedObject, this side only reads
    // cascade ALL + orphanRemoval: deleting the object also deletes its observations
    @OneToMany(mappedBy = "trackedObject",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ObservationSession> observations = new ArrayList<>();

    // JPA needs a no-args constructor, protected is enough
    protected TrackedObject() {}

    public TrackedObject(String name, double estimatedDiameterKm,
                         boolean potentiallyHazardous, String closeApproachDate,
                         double missDistanceKm) {
        this.name = name;
        this.estimatedDiameterKm = estimatedDiameterKm;
        this.potentiallyHazardous = potentiallyHazardous;
        this.closeApproachDate = closeApproachDate;
        this.missDistanceKm = missDistanceKm;
    }

    // getters + setters
    // no setter for id on purpose, only the database writes it

    public Long getId() { return id; }

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

    public List<ObservationSession> getObservations() {
        return observations;
    }

    // helpers keep both sides of the relation in sync
    public void addObservation(ObservationSession observation) {
        observations.add(observation);
        observation.setTrackedObject(this);
    }

    public void removeObservation(ObservationSession observation) {
        observations.remove(observation);
        observation.setTrackedObject(null);
    }
}
