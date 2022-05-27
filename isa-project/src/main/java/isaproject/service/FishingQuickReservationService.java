package isaproject.service;

import java.util.Set;

import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;

public interface FishingQuickReservationService {

	FishingQuickReservationDTO findById(Long id);

	FishingQuickReservationDTO deleteById(Long id);

	Set<FishingQuickReservationDTO> findByFishingCourseId(Long id);

	Set<FishingQuickReservationDTO> findByIsReservedFalseAndFishingCourseId(Long id);

	Set<FishingQuickReservationDTO> findByIsReservedFalse();

	Set<FishingQuickReservationDTO> findAll();

	FishingQuickReservationDTO save(FishingQuickReservationDTO cottageQuickReservation, String siteUrl);

	FishingReservationDTO appointQuickReservation(Long reservationId, Long userId);
}
