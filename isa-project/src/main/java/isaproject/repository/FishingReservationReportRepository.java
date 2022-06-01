package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.FishingReservationReport;

public interface FishingReservationReportRepository extends JpaRepository<FishingReservationReport, Long> {

}
