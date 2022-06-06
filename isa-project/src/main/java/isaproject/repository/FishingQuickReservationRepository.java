package isaproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.FishingQuickReservation;
import isaproject.model.boat.BoatQuickReservation;

public interface FishingQuickReservationRepository extends PagingAndSortingRepository<FishingQuickReservation, Long> {

	List<FishingQuickReservation> findAll();
	
	List<FishingQuickReservation> findByFishingCourse_FishingTrainer_Id(Long id);
	
	@Query(value = " Select * from public.fishing_quick_reservation  LEFT OUTER JOIN  public.fishing_course \r\n"
			+ "on public.fishing_quick_reservation.fishing_course_id = public.fishing_course.id \r\n"
			+ "WHERE fishing_trainer_id = :fishingTrainerId ", countQuery = "Select count(*) from public.fishing_quick_reservation  LEFT OUTER JOIN  public.fishing_course \r\n"
					+ "on public.fishing_quick_reservation.fishing_course_id = public.fishing_course.id \r\n"
					+ "WHERE fishing_trainer_id = :fishingTrainerId ", nativeQuery = true)
	Page<FishingQuickReservation> findAllByFishingTrainerId(@Param("fishingTrainerId") Long fishingTrainerId,
			Pageable paging);

	List<FishingQuickReservation> findByIsReservedFalseAndFishingCourseId(Long id);

	List<FishingQuickReservation> findByIsReservedFalse();
}
