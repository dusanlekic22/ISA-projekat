package isaproject.service;

import java.util.Set;

import isaproject.dto.ReservationReportDTO;

public interface ReservationReportService {

	ReservationReportDTO create(ReservationReportDTO fishingReservationReportDTO);

	Set<ReservationReportDTO> getAllReservationReports();

	ReservationReportDTO approve(ReservationReportDTO fishingReservationReportDTO, String answerCutomer, String answerOwner);

	ReservationReportDTO decline(ReservationReportDTO fishingReservationReportDTO, String answerCutomer, String answerOwner);

	Boolean isReportedByFishingTrainer(Long reservationId);

	Boolean isReportedByCottageOwner(Long reservationId);

	Boolean isReportedByBoatOwner(Long reservationId);

}
