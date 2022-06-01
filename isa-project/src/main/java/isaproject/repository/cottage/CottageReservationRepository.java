package isaproject.repository.cottage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.cottage.CottageReservation;

public interface CottageReservationRepository extends JpaRepository<CottageReservation, Long>{
	List<CottageReservation> findByCottageId(Long id);

	List<CottageReservation> findByCottage_CottageOwner_Id(Long id);
}
