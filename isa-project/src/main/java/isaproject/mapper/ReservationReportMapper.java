package isaproject.mapper;

import isaproject.dto.ReservationReportDTO;
import isaproject.model.ReservationReport;

public class ReservationReportMapper {

	public static ReservationReport DTOToReservationReport(ReservationReportDTO reservationReportDTO) {
		ReservationReport reservationReport = new ReservationReport();
		reservationReport.setUserPenalized(reservationReportDTO.getUserPenalized());
		reservationReport.setComment(reservationReportDTO.getComment());
		reservationReport.setFishingReservation(reservationReportDTO.getFishingReservation());
		reservationReport.setCottageReservation(reservationReportDTO.getCottageReservation());
		reservationReport.setBoatReservation(reservationReportDTO.getBoatReservation());
		reservationReport.setId(reservationReportDTO.getId());
		reservationReport.setReservationReportStatus(reservationReportDTO.getReservationReportStatus());
		return reservationReport;
	}

	public static ReservationReportDTO ReservationReportToDTO(ReservationReport reservationReport) {
		ReservationReportDTO reservationReportDTO = new ReservationReportDTO();
		reservationReportDTO.setUserPenalized(reservationReport.getUserPenalized());
		reservationReportDTO.setComment(reservationReport.getComment());
		reservationReportDTO.setFishingReservation(reservationReport.getFishingReservation());
		reservationReportDTO.setCottageReservation(reservationReport.getCottageReservation());
		reservationReportDTO.setBoatReservation(reservationReport.getBoatReservation());
		reservationReportDTO.setId(reservationReport.getId());
		reservationReportDTO.setReservationReportStatus(reservationReport.getReservationReportStatus());
		return reservationReportDTO;
	}

}
