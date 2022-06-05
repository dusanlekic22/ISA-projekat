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

	List<CottageReservation> findByConfirmedIsTrueAndCottageId(Long id);

	List<CottageReservation> findByConfirmedIsTrueAndCottage_CottageOwner_Id(Long id);

	List<CottageReservation> findByCottage_CottageOwner_Id(Long id);

	Page<CottageReservation> findByCustomerId(Long id, Pageable pageable);

	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation   "
			+ " WHERE customer_id = :customerId and end_date <= NOW() " + " and confirmed = true ",
			countQuery = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation  "
					+ " WHERE customer_id = :customerId and end_date <= NOW() " + " and confirmed = true ", nativeQuery = true)
	Page<CottageReservation> findCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);
	
	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation    "
			+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() )) " + " and confirmed = true ", 
			countQuery = "SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation  "
					+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() ))" + " and confirmed = true ", nativeQuery = true)
	Page<CottageReservation> findIncomingCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);
}
