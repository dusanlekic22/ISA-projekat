package isaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.CottageQuickReservation;

public interface CottageQuickReservationRepository extends JpaRepository<CottageQuickReservation, Long>{

	List<CottageQuickReservation> findByCottageId(Long id);
}
