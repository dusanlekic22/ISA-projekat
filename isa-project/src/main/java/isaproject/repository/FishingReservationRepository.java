package isaproject.repository;

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

import isaproject.model.FishingReservation;

public interface FishingReservationRepository extends PagingAndSortingRepository<FishingReservation, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM FishingReservation f WHERE f.id=:id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	FishingReservation getNotLockedFishingReservation(long id);
	
	Set<FishingReservation> findAll();

	Set<FishingReservation> findByConfirmedIsTrueAndIsCancelledIsFalseAndFishingCourse_FishingTrainer_Id(Long id);

	Set<FishingReservation> findByConfirmedIsTrueAndIsCancelledIsFalseAndFishingCourseId(Long id);
	
	Set<FishingReservation> findByFishingCourseId(Long id);

	Page<FishingReservation> findByCustomerId(Long id, Pageable pageable);

	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.fishing_reservation "
			+ " WHERE customer_id = :customerId and end_date <= NOW()  "
			+ " and confirmed = true and is_cancelled = false", countQuery = " SELECT count(*) FROM public.fishing_reservation "
					+ " WHERE customer_id = :customerId and end_date <= NOW() "
					+ " and confirmed = true and is_cancelled = false", nativeQuery = true)
	Page<FishingReservation> findCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);

	@Query(value = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration FROM public.fishing_reservation  "
			+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() )) "
			+ " and confirmed = true and is_cancelled = false", countQuery = " SELECT *, Extract(epoch FROM (end_date - start_date))/60 AS duration, FROM public.fishing_reservation  "
					+ " WHERE customer_id = :customerId and ( (start_date > NOW()) OR ( start_date< NOW() and end_date > NOW() )) "
					+ " and confirmed = true and is_cancelled = false", nativeQuery = true)
	Page<FishingReservation> findIncomingCustomerReservationsSortByDuration(@Param("customerId") Long customerId,
			Pageable pageable);

	Set<FishingReservation> findByConfirmedIsTrue();
}
