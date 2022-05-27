package isaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.FishingQuickReservation;

public interface FishingQuickReservationRepository extends JpaRepository<FishingQuickReservation, Long> {

	List<FishingQuickReservation> findByFishingCourseId(Long id);

	List<FishingQuickReservation> findByIsReservedFalseAndFishingCourseId(Long id);

	List<FishingQuickReservation> findByIsReservedFalse();
}
