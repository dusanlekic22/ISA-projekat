package isaproject.mapper;

import isaproject.dto.CottageQuickReservationDTO;
import isaproject.dto.CottageReservationDTO;
import isaproject.model.CottageQuickReservation;

public class CottageQuickReservationMapper {
	
	public static CottageQuickReservation CottageQuickReservationDTOToCottageQuickReservation(CottageQuickReservationDTO cottageQuickReservationDTO) {
		CottageQuickReservation cottageQuickReservation = new CottageQuickReservation();
		cottageQuickReservation.setId(cottageQuickReservationDTO.getId());
		cottageQuickReservation.setDuration(cottageQuickReservationDTO.getDuration());
		cottageQuickReservation.setValidSpan(cottageQuickReservationDTO.getValidSpan());
		cottageQuickReservation.setGuestCapacity(cottageQuickReservationDTO.getGuestCapacity());
		cottageQuickReservation.setPrice(cottageQuickReservationDTO.getPrice());
		cottageQuickReservation.setAdditionalService(cottageQuickReservationDTO.getAdditionalService());
		cottageQuickReservation.setCottage(cottageQuickReservationDTO.getCottage());
		return cottageQuickReservation;
	}
	
	public static CottageQuickReservationDTO CottageQuickReservationToCottageQuickReservationDTO(CottageQuickReservation cottageQuickReservation) {
		CottageQuickReservationDTO cottageQuickReservationDTO = new CottageQuickReservationDTO();
		cottageQuickReservationDTO.setId(cottageQuickReservation.getId());
		cottageQuickReservationDTO.setDuration(cottageQuickReservation.getDuration());
		cottageQuickReservationDTO.setValidSpan(cottageQuickReservation.getValidSpan());
		cottageQuickReservationDTO.setGuestCapacity(cottageQuickReservation.getGuestCapacity());
		cottageQuickReservationDTO.setPrice(cottageQuickReservation.getPrice());
		cottageQuickReservationDTO.setAdditionalService(cottageQuickReservation.getAdditionalService());
		cottageQuickReservationDTO.setCottage(cottageQuickReservation.getCottage());
		return cottageQuickReservationDTO;
	}
	
	public static CottageQuickReservation CottageReservationDTOToCottageQuickReservation(CottageReservationDTO cottageReservationDTO) {
		CottageQuickReservation cottageQuickReservation = new CottageQuickReservation();
		cottageQuickReservation.setId(cottageReservationDTO.getId());
		cottageQuickReservation.setDuration(cottageReservationDTO.getDuration());
		cottageQuickReservation.setGuestCapacity(cottageReservationDTO.getGuestCapacity());
		cottageQuickReservation.setPrice(cottageReservationDTO.getPrice());
		cottageQuickReservation.setAdditionalService(cottageReservationDTO.getAdditionalService());
		cottageQuickReservation.setCottage(cottageReservationDTO.getCottage());
		return cottageQuickReservation;
	}

}
