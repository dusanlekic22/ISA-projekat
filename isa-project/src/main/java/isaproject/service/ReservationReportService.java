package isaproject.service;

import java.util.Set;

import isaproject.dto.ReservationReportDTO;

public interface ReservationReportService {

	ReservationReportDTO create(ReservationReportDTO fishingReservationReportDTO);

	Set<ReservationReportDTO> getAllReservationReports();

	ReservationReportDTO approve(ReservationReportDTO fishingReservationReportDTO, String answerCutomer, String answerOwner);

	ReservationReportDTO decline(ReservationReportDTO fishingReservationReportDTO, String answerCutomer, String answerOwner);

}
