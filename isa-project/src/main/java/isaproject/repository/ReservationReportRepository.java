package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.ReservationReport;

public interface ReservationReportRepository extends JpaRepository<ReservationReport, Long> {

	Boolean existsByFishingReservationId(Long reservationId);

	Boolean existsByCottageReservationId(Long reservationId);

	Boolean existsByBoatReservationId(Long reservationId);

}
