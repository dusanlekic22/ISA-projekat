package isaproject.repository.cottage;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.cottage.CottageReservation;

public interface CottageReservationRepository extends PagingAndSortingRepository<CottageReservation, Long> {

	Set<CottageReservation> findAll();

	List<CottageReservation> findByCottageId(Long id);

	List<CottageReservation> findByCottage_CottageOwner_Id(Long id);
	
	Page<CottageReservation> findByCustomerId(Long id, Pageable pageable);

	@Query(value = " SELECT * Extract(epoch FROM (end_date - start_date))/60 AS duration, FROM public.cottage_reservation as cr  "
			+ " WHERE cr.customer_id = customerId ", countQuery = " SELECT count(*) FROM public.cottage_reservation as cr  "
					+ " WHERE cr.customer_id = :customerId ", nativeQuery = true)
	Page<CottageReservation> findCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);
}
