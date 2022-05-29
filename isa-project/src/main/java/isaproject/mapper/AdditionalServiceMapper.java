package isaproject.mapper;

import isaproject.dto.AdditionalServiceDTO;
import isaproject.model.AdditionalService;

public class AdditionalServiceMapper {
	
	public static AdditionalService AdditionalServiceDTOToAdditionalService(AdditionalServiceDTO additionalServiceDTO) {
		AdditionalService additionalService = new AdditionalService();
		additionalService.setId(additionalServiceDTO.getId());
		additionalService.setName(additionalServiceDTO.getName());
		additionalService.setPrice(additionalServiceDTO.getPrice());
		additionalService.setBoat(additionalServiceDTO.getBoat());
		additionalService.setCottage(additionalServiceDTO.getCottage());
		additionalService.setFishingCourse(additionalServiceDTO.getFishingCourse());
		additionalService.setBoat(additionalServiceDTO.getBoat());
		additionalService.setCottageReservation(additionalServiceDTO.getCottageReservation());
		additionalService.setCottageQuickReservation(additionalServiceDTO.getCottageQuickReservation());
		additionalService.setBoatReservation(additionalServiceDTO.getBoatReservation());
		additionalService.setBoatQuickReservation(additionalServiceDTO.getBoatQuickReservation());
		return additionalService;
	}
	
	public static AdditionalServiceDTO AdditionalServiceToAdditionalServiceDTO(AdditionalService additionalService) {
		AdditionalServiceDTO additionalServiceDTO = new AdditionalServiceDTO();
		additionalServiceDTO.setId(additionalService.getId());
		additionalServiceDTO.setName(additionalService.getName());
		additionalServiceDTO.setPrice(additionalService.getPrice());
		additionalServiceDTO.setBoat(additionalService.getBoat());
		additionalServiceDTO.setCottage(additionalService.getCottage());
		additionalServiceDTO.setFishingCourse(additionalService.getFishingCourse());
		additionalServiceDTO.setBoat(additionalService.getBoat());
		additionalServiceDTO.setCottageReservation(additionalService.getCottageReservation());
		additionalServiceDTO.setCottageQuickReservation(additionalService.getCottageQuickReservation());
		additionalServiceDTO.setBoatReservation(additionalService.getBoatReservation());
		additionalServiceDTO.setBoatQuickReservation(additionalService.getBoatQuickReservation());
		return additionalServiceDTO;
	}

}
