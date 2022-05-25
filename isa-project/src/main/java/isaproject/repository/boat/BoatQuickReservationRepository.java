package isaproject.repository.boat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.boat.BoatQuickReservation;

public interface BoatQuickReservationRepository extends JpaRepository<BoatQuickReservation, Long>{

	List<BoatQuickReservation> findByBoatId(Long id);
	
	List<BoatQuickReservation> findByIsReservedFalseAndBoatId(Long id);
	
	List<BoatQuickReservation> findByIsReservedFalse();
}