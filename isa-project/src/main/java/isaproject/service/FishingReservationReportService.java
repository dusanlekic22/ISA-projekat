package isaproject.service;

import java.util.Set;

import isaproject.dto.FishingReservationReportDTO;

public interface FishingReservationReportService {

	FishingReservationReportDTO create(FishingReservationReportDTO fishingReservationReportDTO);

	Set<FishingReservationReportDTO> getAllFishingReservationReports();

	FishingReservationReportDTO approve(FishingReservationReportDTO fishingReservationReportDTO, String answerCutomer, String answerOwner);

	FishingReservationReportDTO decline(FishingReservationReportDTO fishingReservationReportDTO, String answerCutomer, String answerOwner);

}
