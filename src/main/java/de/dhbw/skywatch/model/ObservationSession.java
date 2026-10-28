package de.dhbw.skywatch.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// One observation of a tracked object (who, when, where)
@Entity
public class ObservationSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observerName;
    private LocalDateTime observedAt;
    private String location;
    private String notes;

    // many observations belong to one object -> FK column tracked_object_id
    // LAZY: the parent only gets loaded when we actually access it
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tracked_object_id")
    private TrackedObject trackedObject;

    // for JPA only
    protected ObservationSession() {}

    public ObservationSession(String observerName, LocalDateTime observedAt,
                              String location, String notes,
                              TrackedObject trackedObject) {
        this.observerName = observerName;
        this.observedAt = observedAt;
        this.location = location;
        this.notes = notes;
        this.trackedObject = trackedObject;
    }

    public Long getId() { return id; }

    public String getObserverName() { return observerName; }
    public void setObserverName(String v) { this.observerName = v; }

    public LocalDateTime getObservedAt() { return observedAt; }
    public void setObservedAt(LocalDateTime v) { this.observedAt = v; }

    public String getLocation() { return location; }
    public void setLocation(String v) { this.location = v; }

    public String getNotes() { return notes; }
    public void setNotes(String v) { this.notes = v; }

    public TrackedObject getTrackedObject() { return trackedObject; }
    public void setTrackedObject(TrackedObject v) { this.trackedObject = v; }
}
