package isaproject.mapper;

import isaproject.dto.CottageQuickReservationDTO;
import isaproject.model.CottageQuickReservation;

public class CottageQuickReservationMapper {
	
	public static CottageQuickReservation CottageQuickReservationDTOToCottageQuickReservation(CottageQuickReservationDTO cottageQuickReservationDTO) {
		CottageQuickReservation cottageQuickReservation = new CottageQuickReservation();
		cottageQuickReservation.setId(cottageQuickReservationDTO.getId());
		cottageQuickReservation.setStartDate(cottageQuickReservationDTO.getStartDate());
		cottageQuickReservation.setEndDate(cottageQuickReservationDTO.getEndDate());
		cottageQuickReservation.setGuestCapacity(cottageQuickReservationDTO.getGuestCapacity());
		cottageQuickReservation.setPrice(cottageQuickReservationDTO.getPrice());
		cottageQuickReservation.setAdditionalService(cottageQuickReservationDTO.getAdditionalService());
		cottageQuickReservation.setCottage(cottageQuickReservationDTO.getCottage());
		return cottageQuickReservation;
	}
	
	public static CottageQuickReservationDTO CottageQuickReservationToCottageQuickReservationDTO(CottageQuickReservation cottageQuickReservation) {
		CottageQuickReservationDTO cottageQuickReservationDTO = new CottageQuickReservationDTO();
		cottageQuickReservationDTO.setId(cottageQuickReservation.getId());
		cottageQuickReservationDTO.setStartDate(cottageQuickReservation.getStartDate());
		cottageQuickReservationDTO.setEndDate(cottageQuickReservation.getEndDate());
		cottageQuickReservationDTO.setGuestCapacity(cottageQuickReservation.getGuestCapacity());
		cottageQuickReservationDTO.setPrice(cottageQuickReservation.getPrice());
		cottageQuickReservationDTO.setAdditionalService(cottageQuickReservation.getAdditionalService());
		cottageQuickReservationDTO.setCottage(cottageQuickReservation.getCottage());
		return cottageQuickReservationDTO;
	}

}
