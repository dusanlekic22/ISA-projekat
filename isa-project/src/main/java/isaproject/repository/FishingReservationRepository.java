package isaproject.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.FishingReservation;

public interface FishingReservationRepository extends JpaRepository<FishingReservation, Long>  {

	Set<FishingReservation> findByFishingCourseId(Long id);

}
