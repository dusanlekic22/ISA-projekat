package isaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.AdditionalService;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {

	List<AdditionalService> findByCottageIsNull();

	List<AdditionalService> findByCottageId(Long id);

	List<AdditionalService> findByCottageReservationId(Long id);

	List<AdditionalService> findByCottageQuickReservationId(Long id);

	List<AdditionalService> findByBoatId(Long id);

	List<AdditionalService> findByBoatReservationId(Long id);

	List<AdditionalService> findByBoatQuickReservationId(Long id);
	
	List<AdditionalService> findByFishingCourseId(Long id);

	List<AdditionalService> findByFishingReservationId(Long id);
	
	List<AdditionalService> findByFishingQuickReservationId(Long id);
}
