package isaproject.mapper;

import java.util.HashSet;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.dto.cottage.CottageQuickReservationDTO;
import isaproject.model.FishingQuickReservation;
import isaproject.model.FishingReservation;
import isaproject.model.cottage.CottageQuickReservation;

public class FishingQuickReservationMapper {
	
	public static FishingQuickReservation DTOToFishingQuickReservation(FishingQuickReservationDTO fishingQuickReservationDTO) {
		FishingQuickReservation fishingQuickReservation = new FishingQuickReservation();
		fishingQuickReservation.setId(fishingQuickReservationDTO.getId());
		fishingQuickReservation.setDuration(fishingQuickReservationDTO.getDuration());
		fishingQuickReservation.setCapacity(fishingQuickReservationDTO.getCapacity());
		fishingQuickReservation.setPrice(fishingQuickReservationDTO.getPrice());
		fishingQuickReservation.setLocation(fishingQuickReservationDTO.getLocation());
		fishingQuickReservation.setAdditionalService(fishingQuickReservationDTO.getAdditionalService());
		fishingQuickReservation.setFishingCourse(fishingQuickReservationDTO.getFishingCourse());
		fishingQuickReservation.setReserved(fishingQuickReservationDTO.isReserved());
		return fishingQuickReservation;
	}
	
	public static FishingQuickReservationDTO FishingQuickReservationToDTO(FishingQuickReservation fishingQuickReservation) {
		FishingQuickReservationDTO fishingQuickReservationDTO = new FishingQuickReservationDTO();
		fishingQuickReservationDTO.setId(fishingQuickReservation.getId());
		fishingQuickReservationDTO.setDuration(fishingQuickReservation.getDuration());
		fishingQuickReservationDTO.setCapacity(fishingQuickReservation.getCapacity());
		fishingQuickReservationDTO.setPrice(fishingQuickReservation.getPrice());
		fishingQuickReservationDTO.setLocation(fishingQuickReservation.getLocation());
		fishingQuickReservationDTO.setAdditionalService(fishingQuickReservation.getAdditionalService());
		fishingQuickReservationDTO.setFishingCourse(fishingQuickReservation.getFishingCourse());
		fishingQuickReservationDTO.setReserved(fishingQuickReservation.isReserved());
		return fishingQuickReservationDTO;
	}
	
	public static FishingQuickReservation FishingReservationDTOToFishingQuickReservation(FishingReservationDTO fishingReservationDTO) {
		FishingQuickReservation fishingQuickReservation = new FishingQuickReservation();
		fishingQuickReservation.setId(fishingReservationDTO.getId());
		fishingQuickReservation.setDuration(fishingReservationDTO.getDuration());
		fishingQuickReservation.setCapacity(fishingReservationDTO.getCapacity());
		fishingQuickReservation.setPrice(fishingReservationDTO.getPrice());
		fishingQuickReservation.setLocation(fishingReservationDTO.getLocation());
		fishingQuickReservation.setAdditionalService(fishingReservationDTO.getAdditionalService());
		fishingQuickReservation.setFishingCourse(fishingReservationDTO.getFishingCourse());
		return fishingQuickReservation;
	}
	
	public static FishingReservation FishingQuickReservationToFishingReservation(FishingQuickReservation fishingQuickReservation) {
		FishingReservation fishingReservation = new FishingReservation();
		fishingReservation.setDuration(fishingQuickReservation.getDuration());
		fishingReservation.setCapacity(fishingQuickReservation.getCapacity());
		fishingReservation.setPrice(fishingQuickReservation.getPrice());
		fishingReservation.setLocation(fishingQuickReservation.getLocation());
		fishingReservation.setAdditionalService(new HashSet<>(fishingQuickReservation.getAdditionalService()));
		fishingReservation.setFishingCourse(fishingQuickReservation.getFishingCourse());
		fishingReservation.setConfirmed(true);
		return fishingReservation;
	}
	
	public static Page<FishingQuickReservationDTO> pageFishingQuickReservationToPageFishingQuickReservationDTO(
			Page<FishingQuickReservation> fishingReservationPages) {
			Page<FishingQuickReservationDTO> fishingReservationDTOPages = fishingReservationPages.map(new Function<FishingQuickReservation, FishingQuickReservationDTO>() {
				@Override
				public FishingQuickReservationDTO apply(FishingQuickReservation entity) {
					FishingQuickReservationDTO dto = FishingQuickReservationMapper.FishingQuickReservationToDTO(entity);
					return dto;
				}
			});
			return fishingReservationDTOPages;
	}
	

}
