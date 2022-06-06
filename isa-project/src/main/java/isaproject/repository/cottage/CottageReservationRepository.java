package isaproject.repository.cottage;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.cottage.CottageReservation;

public interface CottageReservationRepository extends PagingAndSortingRepository<CottageReservation, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CottageReservation c WHERE c.id=:id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	CottageReservation getNotLockedCottageReservation(long id);
	
	Set<CottageReservation> findAll();

	List<CottageReservation> findByCottageId(Long id);

	List<CottageReservation> findByConfirmedIsTrueAndIsCancelledIsFalseAndCottageId(Long id);

	List<CottageReservation> findByConfirmedIsTrueAndIsCancelledIsFalseAndCottage_CottageOwner_Id(Long id);

	List<CottageReservation> findByCottage_CottageOwner_Id(Long id);

	Page<CottageReservation> findByCustomerId(Long id, Pageable pageable);

	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation   "
			+ " WHERE customer_id = :customerId and end_date <= NOW() " + " and confirmed = true and is_cancelled = false",
			countQuery = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation  "
					+ " WHERE customer_id = :customerId and end_date <= NOW() " + " and confirmed = true and is_cancelled = false", nativeQuery = true)
	Page<CottageReservation> findCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);
	
	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation    "
			+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() )) " + " and confirmed = true and is_cancelled = false", 
			countQuery = "SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.cottage_reservation  "
					+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() ))" + " and confirmed = true and is_cancelled = false", nativeQuery = true)
	Page<CottageReservation> findIncomingCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);
	
	List<CottageReservation> findByConfirmedIsTrue();
}
