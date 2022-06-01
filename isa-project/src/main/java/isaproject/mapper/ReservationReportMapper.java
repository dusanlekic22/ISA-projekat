package isaproject.mapper;

import isaproject.dto.FishingReservationReportDTO;
import isaproject.model.FishingReservationReport;

public class ReservationReportMapper {

	public static FishingReservationReport DTOToFishingReservationReport(
			FishingReservationReportDTO fishingReservationReportDTO) {
		FishingReservationReport fishingReservationReport = new FishingReservationReport();
		fishingReservationReport.setUserPenalized(fishingReservationReportDTO.getUserPenalized());
		fishingReservationReport.setComment(fishingReservationReportDTO.getComment());
		fishingReservationReport.setFishingReservation(fishingReservationReportDTO.getFishingReservation());
		fishingReservationReport.setId(fishingReservationReportDTO.getId());
		fishingReservationReport.setReservationReportStatus(fishingReservationReportDTO.getReservationReportStatus());
		return fishingReservationReport;
	}

	public static FishingReservationReportDTO FishingReservationReportToDTO(
			FishingReservationReport fishingReservationReport) {
		FishingReservationReportDTO fishingReservationReportDTO = new FishingReservationReportDTO();
		fishingReservationReportDTO.setUserPenalized(fishingReservationReport.getUserPenalized());
		fishingReservationReportDTO.setComment(fishingReservationReport.getComment());
		fishingReservationReportDTO.setFishingReservation(fishingReservationReport.getFishingReservation());
		fishingReservationReportDTO.setId(fishingReservationReport.getId());
		fishingReservationReportDTO.setReservationReportStatus(fishingReservationReport.getReservationReportStatus());
		return fishingReservationReportDTO;
	}

}
