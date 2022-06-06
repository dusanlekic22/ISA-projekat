package isaproject.repository.cottage;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import isaproject.model.cottage.CottageQuickReservation;

public interface CottageQuickReservationRepository extends JpaRepository<CottageQuickReservation, Long>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CottageQuickReservation c WHERE c.id=:id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	CottageQuickReservation getNotLockedCottageQuickReservation(long id);

	List<CottageQuickReservation> findByCottageId(Long id);
	
	List<CottageQuickReservation> findByIsReservedFalseAndCottageId(Long id);
	
	List<CottageQuickReservation> findByIsReservedFalse();

	List<CottageQuickReservation> findByCottage_CottageOwner_Id(Long id);
}
