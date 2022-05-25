package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingReservationDTO;
import isaproject.mapper.FishingReservationMapper;
import isaproject.model.FishingReservation;
import isaproject.repository.FishingReservationRepository;
import isaproject.service.FishingReservationService;

@Service
public class FishingReservationServiceImpl implements FishingReservationService {

	private FishingReservationRepository ReservationRepository;

	@Autowired
	public FishingReservationServiceImpl(FishingReservationRepository ReservationRepository) {
		super();
		this.ReservationRepository = ReservationRepository;
	}

	@Override
	public Set<FishingReservationDTO> findByFishingCourseId(Long id) {
		Set<FishingReservation> fishingReservations = new HashSet<>(ReservationRepository.findByFishingCourseId(id));
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservation reservation : fishingReservations) {
			dtos.add(FishingReservationMapper.FishingReservationToDTO(reservation));
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllActiveByFishingCourseId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO ReservationDTO : findByFishingCourseId(id)) {
			if (!ReservationDTO.getDuration().passed())
				dtos.add(ReservationDTO);
		}
		return dtos;
	}

}
