package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.ReservationSpan;

public interface ReservationSpanRepository extends JpaRepository<ReservationSpan, Long> {
	
	
}
