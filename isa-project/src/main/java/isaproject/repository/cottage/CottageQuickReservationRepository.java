package isaproject.repository.cottage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.cottage.CottageQuickReservation;

public interface CottageQuickReservationRepository extends JpaRepository<CottageQuickReservation, Long>{

	List<CottageQuickReservation> findByCottageId(Long id);
	
	List<CottageQuickReservation> findByIsReservedFalseAndCottageId(Long id);
	
	List<CottageQuickReservation> findByIsReservedFalse();

	List<CottageQuickReservation> findByCottage_CottageOwner_Id(Long id);
}
