package isaproject.mapper;

import isaproject.dto.CottageDTO;
import isaproject.model.Cottage;

public class CottageMapper {
	
	public static Cottage CottageDTOToCottage(CottageDTO cottageDTO) {
		Cottage cottage = new Cottage();
		cottage.setId(cottageDTO.getId());
		cottage.setName(cottageDTO.getName());
		cottage.setAddress(cottageDTO.getAddress());
		cottage.setPromoDescription(cottageDTO.getPromoDescription());
		cottage.setBedCount(cottageDTO.getBedCount());
		cottage.setRoomCount(cottageDTO.getRoomCount());
		cottage.setAdditionalService(cottageDTO.getAdditionalService());
		cottage.setCottageImage(cottageDTO.getCottageImage());
		cottage.setCottageOwner(cottageDTO.getCottageOwner());
		cottage.setCottageQuickReservation(cottageDTO.getCottageQuickReservation());
		cottage.setCottageReservation(cottageDTO.getCottageReservation());
		cottage.setCottageRules(cottageDTO.getCottageRules());
		cottage.setAvailableReservationDateSpan(cottageDTO.getAvailableReservationDateSpan());
		return cottage;
	}
	
	public static CottageDTO CottageToCottageDTO(Cottage cottage) {
		CottageDTO cottageDTO = new CottageDTO();
		cottageDTO.setId(cottage.getId());
		cottageDTO.setName(cottage.getName());
		cottageDTO.setAddress(cottage.getAddress());
		cottageDTO.setPromoDescription(cottage.getPromoDescription());
		cottageDTO.setBedCount(cottage.getBedCount());
		cottageDTO.setRoomCount(cottage.getRoomCount());
		cottageDTO.setAdditionalService(cottage.getAdditionalService());
		cottageDTO.setCottageImage(cottage.getCottageImage());
		cottageDTO.setCottageOwner(cottage.getCottageOwner());
		cottageDTO.setCottageQuickReservation(cottage.getCottageQuickReservation());
		cottageDTO.setCottageReservation(cottage.getCottageReservation());
		cottageDTO.setCottageRules(cottage.getCottageRules());
		cottageDTO.setAvailableReservationDateSpan(cottage.getAvailableReservationDateSpan());
		return cottageDTO;
	}
}
