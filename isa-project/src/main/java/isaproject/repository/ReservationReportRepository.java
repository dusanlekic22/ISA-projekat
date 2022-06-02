package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.ReservationReport;

public interface ReservationReportRepository extends JpaRepository<ReservationReport, Long> {

}
