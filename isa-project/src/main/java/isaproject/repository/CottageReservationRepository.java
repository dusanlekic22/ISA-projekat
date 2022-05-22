package isaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.CottageReservation;

public interface CottageReservationRepository extends JpaRepository<CottageReservation, Long>{
	List<CottageReservation> findByCottageId(Long id);
}
