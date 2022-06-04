package isaproject.repository.boat;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.CottageReservation;

public interface BoatReservationRepository extends PagingAndSortingRepository<BoatReservation, Long>{
	
	Set<BoatReservation> findAll();
	
	List<BoatReservation> findByBoatId(Long id);
	
	List<BoatReservation> findByBoat_BoatOwner_Id(Long id);
	
	Page<BoatReservation> findByCustomerId(Long id, Pageable pageable);
	
	@Query(value = " SELECT * Extract(epoch FROM (end_date - start_date))/60 AS duration, FROM public.boat_reservation as br  "
			+ " WHERE br.customer_id = customerId ", countQuery = " SELECT count(*) FROM public.boat_reservation as br  "
					+ " WHERE br.customer_id = :customerId ", nativeQuery = true)
	Page<BoatReservation> findCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);
}

