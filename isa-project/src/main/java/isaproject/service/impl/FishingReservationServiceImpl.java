package isaproject.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import isaproject.dto.FishingReservationDTO;
import isaproject.service.FishingReservationService;

@Service
public class FishingReservationServiceImpl implements FishingReservationService {

	@Override
	public Set<FishingReservationDTO> findAllActiveByFishingCourseId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
