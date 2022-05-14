package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.CottageReservation;

public interface CottageReservationRepository extends JpaRepository<CottageReservation, Long>{

}
