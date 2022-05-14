package isaproject.mapper;

import isaproject.dto.CottageReservationDTO;
import isaproject.model.CottageReservation;

public class CottageReservationMapper {
	
	public static CottageReservation CottageReservationDTOToCottageReservation(CottageReservationDTO cottageReservationDTO) {
		CottageReservation cottageReservation = new CottageReservation();
		cottageReservation.setId(cottageReservationDTO.getId());
		cottageReservation.setDateSpan(cottageReservationDTO.getDateSpan());
		cottageReservation.setGuestCapacity(cottageReservationDTO.getGuestCapacity());
		cottageReservation.setPrice(cottageReservationDTO.getPrice());
		cottageReservation.setAdditionalService(cottageReservationDTO.getAdditionalService());
		cottageReservation.setCottage(cottageReservationDTO.getCottage());
		cottageReservation.setCustomer(cottageReservationDTO.getCustomer());
		return cottageReservation;
	}
	
	public static CottageReservationDTO CottageReservationToCottageReservationDTO(CottageReservation cottageReservation) {
		CottageReservationDTO cottageReservationDTO = new CottageReservationDTO();
		cottageReservationDTO.setId(cottageReservation.getId());
		cottageReservationDTO.setDateSpan(cottageReservation.getDateSpan());
		cottageReservationDTO.setGuestCapacity(cottageReservation.getGuestCapacity());
		cottageReservationDTO.setPrice(cottageReservation.getPrice());
		cottageReservationDTO.setAdditionalService(cottageReservation.getAdditionalService());
		cottageReservationDTO.setCottage(cottageReservation.getCottage());
		cottageReservationDTO.setCustomer(cottageReservation.getCustomer());
		return cottageReservationDTO;
	}

}
