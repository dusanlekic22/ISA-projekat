package isaproject.repository.boat;

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

import isaproject.model.boat.BoatReservation;

public interface BoatReservationRepository extends PagingAndSortingRepository<BoatReservation, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BoatReservation b WHERE b.id=:id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	BoatReservation getNotLockedBoatReservation(long id);
	
	Set<BoatReservation> findAll();

	List<BoatReservation> findByBoatId(Long id);

	List<BoatReservation> findByBoat_BoatOwner_Id(Long id);
	
	List<BoatReservation> findByConfirmedIsTrueAndBoatId(Long id);
	
	List<BoatReservation> findByConfirmedIsTrueAndBoat_BoatOwner_Id(Long id);

	List<BoatReservation> findByConfirmedIsTrue();

	Page<BoatReservation> findByCustomerId(Long id, Pageable pageable);

	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.boat_reservation  "
			+ " WHERE customer_id = :customerId and end_date <= NOW() "
			+ " and confirmed = true ", countQuery = " SELECT count(*) FROM public.boat_reservation   "
					+ " WHERE customer_id = :customerId and end_date <= NOW() "
					+ " and confirmed = true ", nativeQuery = true)
	Page<BoatReservation> findCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);

	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.boat_reservation   "
			+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() )) "
			+ " and confirmed = true ", countQuery = " SELECT count(*) FROM public.boat_reservation  "
					+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() )) "
					+ " and confirmed = true ", nativeQuery = true)
	Page<BoatReservation> findIncomingCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);
}
