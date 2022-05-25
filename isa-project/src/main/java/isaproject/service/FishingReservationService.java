package isaproject.service;

import java.util.Set;

import isaproject.dto.FishingReservationDTO;

public interface FishingReservationService {
	Set<FishingReservationDTO> findAllActiveByFishingCourseId(Long id);

	Set<FishingReservationDTO> findByFishingCourseId(Long id);
}
