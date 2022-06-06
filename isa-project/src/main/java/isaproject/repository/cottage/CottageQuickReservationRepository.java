package isaproject.repository.cottage;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.cottage.CottageQuickReservation;

public interface CottageQuickReservationRepository extends PagingAndSortingRepository<CottageQuickReservation, Long> {

	List<CottageQuickReservation> findAll();

	@Query(value = " Select * from public.cottage_quick_reservation natural join public.cottage  "
			+ " WHERE cottage_owner_id = :cottageOwnerId ", countQuery = " Select count(*) from public.cottage_quick_reservation natural join public.cottage "
					+ " WHERE cottage_owner_id = :cottageOwnerId ", nativeQuery = true)
	Page<CottageQuickReservation> findAllByCottageOwnerId(@Param("cottageOwnerId") Long cottageOwnerId,
			Pageable paging);

	List<CottageQuickReservation> findByCottageId(Long id);

	List<CottageQuickReservation> findByIsReservedFalseAndCottageId(Long id);

	List<CottageQuickReservation> findByIsReservedFalse();

	List<CottageQuickReservation> findByCottage_CottageOwner_Id(Long id);
}
