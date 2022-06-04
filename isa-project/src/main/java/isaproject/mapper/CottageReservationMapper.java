package isaproject.mapper;

import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.cottage.CottageDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageReservation;

public class CottageReservationMapper {
	
	public static CottageReservation CottageReservationDTOToCottageReservation(CottageReservationDTO cottageReservationDTO) {
		CottageReservation cottageReservation = new CottageReservation();
		cottageReservation.setId(cottageReservationDTO.getId());
		cottageReservation.setDuration(new DateTimeSpan(cottageReservationDTO.getDuration()));
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
		cottageReservationDTO.setDuration(new DateTimeSpan(cottageReservation.getDuration()));
		cottageReservationDTO.setGuestCapacity(cottageReservation.getGuestCapacity());
		cottageReservationDTO.setPrice(cottageReservation.getPrice());
		cottageReservationDTO.setAdditionalService(cottageReservation.getAdditionalService());
		cottageReservationDTO.setCottage(cottageReservation.getCottage());
		cottageReservationDTO.setCustomer(cottageReservation.getCustomer());
		cottageReservationDTO.setConfirmed(cottageReservation.isConfirmed());
		return cottageReservationDTO;
	}

	public static Page<CottageReservationDTO> pageCottageReservationToPageCottageReservationDTO(
			Page<CottageReservation> cottageReservationPages) {
			Page<CottageReservationDTO> cottageReservationDTOPages = cottageReservationPages.map(new Function<CottageReservation, CottageReservationDTO>() {
				@Override
				public CottageReservationDTO apply(CottageReservation entity) {
					CottageReservationDTO dto = CottageReservationMapper.CottageReservationToCottageReservationDTO(entity);
					return dto;
				}
			});
			return cottageReservationDTOPages;
	}

}
