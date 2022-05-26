package isaproject.mapper;

import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.model.cottage.CottageReservation;

public class CottageReservationMapper {
	
	public static CottageReservation CottageReservationDTOToCottageReservation(CottageReservationDTO cottageReservationDTO) {
		CottageReservation cottageReservation = new CottageReservation();
		cottageReservation.setId(cottageReservationDTO.getId());
		cottageReservation.setDuration(cottageReservationDTO.getDuration());
		cottageReservation.setGuestCapacity(cottageReservationDTO.getGuestCapacity());
		cottageReservation.setPrice(cottageReservationDTO.getPrice());
		cottageReservation.setAdditionalService(cottageReservationDTO.getAdditionalService());
		cottageReservation.setCottage(cottageReservationDTO.getCottage());
		cottageReservation.setCustomer(cottageReservationDTO.getCustomer());
		cottageReservation.setConfirmed(cottageReservationDTO.isConfirmed());
		return cottageReservation;
	}
	
	public static CottageReservationDTO CottageReservationToCottageReservationDTO(CottageReservation cottageReservation) {
		CottageReservationDTO cottageReservationDTO = new CottageReservationDTO();
		cottageReservationDTO.setId(cottageReservation.getId());
		cottageReservationDTO.setDuration(cottageReservation.getDuration());
		cottageReservationDTO.setGuestCapacity(cottageReservation.getGuestCapacity());
		cottageReservationDTO.setPrice(cottageReservation.getPrice());
		cottageReservationDTO.setAdditionalService(cottageReservation.getAdditionalService());
		cottageReservationDTO.setCottage(cottageReservation.getCottage());
		cottageReservationDTO.setCustomer(cottageReservation.getCustomer());
		cottageReservationDTO.setConfirmed(cottageReservation.isConfirmed());
		return cottageReservationDTO;
	}

}
