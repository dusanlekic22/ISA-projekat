package isaproject.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.dto.cottage.CottageQuickReservationDTO;

public interface FishingQuickReservationService {

	FishingQuickReservationDTO findById(Long id);

	FishingQuickReservationDTO deleteById(Long id);

	Set<FishingQuickReservationDTO> findByIsReservedFalseAndFishingCourseId(Long id);

	Set<FishingQuickReservationDTO> findByIsReservedFalse();

	Set<FishingQuickReservationDTO> findAll();

	FishingQuickReservationDTO save(FishingQuickReservationDTO cottageQuickReservation, String siteUrl);

	FishingReservationDTO appointQuickReservation(Long reservationId, Long userId);

	Set<FishingQuickReservationDTO> findByFishingCourseFishingTrainerId(Long id);

	Set<FishingQuickReservationDTO> findByFishingTrainerId(Long id);
	
	Page<FishingQuickReservationDTO> findAllPagination(Long fishingTrainerId,Pageable paging);
}
