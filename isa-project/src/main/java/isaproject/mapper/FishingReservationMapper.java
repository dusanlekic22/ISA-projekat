package isaproject.mapper;

import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.FishingReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.model.FishingReservation;
import isaproject.model.cottage.CottageReservation;

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
	
	public static Page<FishingReservationDTO> pageFishingReservationToPageFishingReservationDTO(
			Page<FishingReservation> fishingReservationPages) {
			Page<FishingReservationDTO> fishingReservationDTOPages = fishingReservationPages.map(new Function<FishingReservation, FishingReservationDTO>() {
				@Override
				public FishingReservationDTO apply(FishingReservation entity) {
					FishingReservationDTO dto = FishingReservationMapper.FishingReservationToDTO(entity);
					return dto;
				}
			});
			return fishingReservationDTOPages;
	}
	

}
