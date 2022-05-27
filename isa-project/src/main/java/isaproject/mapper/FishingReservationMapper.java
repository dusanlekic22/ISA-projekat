package isaproject.mapper;

import isaproject.dto.FishingReservationDTO;
import isaproject.model.FishingReservation;

public class FishingReservationMapper {

	public static FishingReservation DTOToFishingReservation(FishingReservationDTO fishingReservationDTO) {
		FishingReservation fishingReservation = new FishingReservation();
		fishingReservation.setId(fishingReservationDTO.getId());
		fishingReservation.setDuration(fishingReservationDTO.getDuration());
		fishingReservation.setCapacity(fishingReservationDTO.getCapacity());
		fishingReservation.setPrice(fishingReservationDTO.getPrice());
		fishingReservation.setLocation(fishingReservationDTO.getLocation());
		fishingReservation.setAdditionalService(fishingReservationDTO.getAdditionalService());
		fishingReservation.setFishingCourse(fishingReservationDTO.getFishingCourse());
		fishingReservation.setCustomer(fishingReservationDTO.getCustomer());
		fishingReservation.setConfirmed(fishingReservationDTO.isConfirmed());
		return fishingReservation;
	}

	public static FishingReservationDTO FishingReservationToDTO(FishingReservation fishingReservation) {
		FishingReservationDTO fishingReservationDTO = new FishingReservationDTO();
		fishingReservationDTO.setId(fishingReservation.getId());
		fishingReservationDTO.setDuration(fishingReservation.getDuration());
		fishingReservationDTO.setCapacity(fishingReservation.getCapacity());
		fishingReservationDTO.setPrice(fishingReservation.getPrice());
		fishingReservationDTO.setLocation(fishingReservation.getLocation());
		fishingReservationDTO.setAdditionalService(fishingReservation.getAdditionalService());
		fishingReservationDTO.setFishingCourse(fishingReservation.getFishingCourse());
		fishingReservationDTO.setCustomer(fishingReservation.getCustomer());
		fishingReservationDTO.setConfirmed(fishingReservation.isConfirmed());
		return fishingReservationDTO;
	}

}
