package isaproject.repository.boat;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.boat.BoatQuickReservation;

public interface BoatQuickReservationRepository extends PagingAndSortingRepository<BoatQuickReservation, Long>{

	List<BoatQuickReservation> findAll();
	
	@Query(value = " Select * from public.boat_quick_reservation natural join public.boat  "
			+ " WHERE boat_owner_id = :boatOwnerId ", countQuery = " Select count(*) from public.boat_quick_reservation natural join public.boat "
					+ " WHERE boat_owner_id = :boatOwnerId ", nativeQuery = true)
	Page<BoatQuickReservation> findAllByBoatOwnerId(@Param("boatOwnerId") Long boatOwnerId,
			Pageable paging);
	
	List<BoatQuickReservation> findByBoatId(Long id);
	
	List<BoatQuickReservation> findByIsReservedFalseAndBoatId(Long id);
	
	List<BoatQuickReservation> findByIsReservedFalse();

	List<BoatQuickReservation> findByBoat_BoatOwner_Id(Long id);

}