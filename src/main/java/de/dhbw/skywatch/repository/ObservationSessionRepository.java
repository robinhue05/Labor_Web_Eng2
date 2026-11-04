package de.dhbw.skywatch.repository;

import de.dhbw.skywatch.model.ObservationSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObservationSessionRepository extends JpaRepository<ObservationSession, Long> {

    // derived query on the FK: WHERE tracked_object_id = ?
    List<ObservationSession> findByTrackedObjectId(Long trackedObjectId);

    // only finds it if it also belongs to that tracked object
    Optional<ObservationSession> findByIdAndTrackedObjectId(Long id, Long trackedObjectId);
}
