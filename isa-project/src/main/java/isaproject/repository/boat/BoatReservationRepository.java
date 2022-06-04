package isaproject.repository.boat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.boat.BoatReservation;

public interface BoatReservationRepository extends JpaRepository<BoatReservation, Long>{
	List<BoatReservation> findByConfirmedIsTrueAndBoatId(Long id);
	
	List<BoatReservation> findByConfirmedIsTrueAndBoat_BoatOwner_Id(Long id);
}

