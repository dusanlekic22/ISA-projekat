package isaproject.mapper;

import java.util.HashSet;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.cottage.CottageQuickReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.model.cottage.CottageQuickReservation;
import isaproject.model.cottage.CottageReservation;

public class CottageQuickReservationMapper {
	
	public static CottageQuickReservation CottageQuickReservationDTOToCottageQuickReservation(CottageQuickReservationDTO cottageQuickReservationDTO) {
		CottageQuickReservation cottageQuickReservation = new CottageQuickReservation();
		cottageQuickReservation.setId(cottageQuickReservationDTO.getId());
		cottageQuickReservation.setDuration(cottageQuickReservationDTO.getDuration());
		cottageQuickReservation.setGuestCapacity(cottageQuickReservationDTO.getGuestCapacity());
		cottageQuickReservation.setPrice(cottageQuickReservationDTO.getPrice());
		cottageQuickReservation.setAdditionalService(cottageQuickReservationDTO.getAdditionalService());
		cottageQuickReservation.setCottage(cottageQuickReservationDTO.getCottage());
		cottageQuickReservation.setReserved(cottageQuickReservationDTO.isReserved());
		return cottageQuickReservation;
	}
	
	public static CottageQuickReservationDTO CottageQuickReservationToCottageQuickReservationDTO(CottageQuickReservation cottageQuickReservation) {
		CottageQuickReservationDTO cottageQuickReservationDTO = new CottageQuickReservationDTO();
		cottageQuickReservationDTO.setId(cottageQuickReservation.getId());
		cottageQuickReservationDTO.setDuration(cottageQuickReservation.getDuration());
		cottageQuickReservationDTO.setGuestCapacity(cottageQuickReservation.getGuestCapacity());
		cottageQuickReservationDTO.setPrice(cottageQuickReservation.getPrice());
		cottageQuickReservationDTO.setAdditionalService(cottageQuickReservation.getAdditionalService());
		cottageQuickReservationDTO.setCottage(cottageQuickReservation.getCottage());
		cottageQuickReservationDTO.setReserved(cottageQuickReservation.isReserved());
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
	
	public static CottageReservation CottageQuickReservationToCottageReservation(CottageQuickReservation cottageQuickReservation) {
		CottageReservation cottageReservation = new CottageReservation();
		cottageReservation.setDuration(cottageQuickReservation.getDuration());
		cottageReservation.setGuestCapacity(cottageQuickReservation.getGuestCapacity());
		cottageReservation.setPrice(cottageQuickReservation.getPrice());
		cottageReservation.setAdditionalService(new HashSet<>(cottageQuickReservation.getAdditionalService()));
		cottageReservation.setCottage(cottageQuickReservation.getCottage());
		cottageReservation.setConfirmed(true);
		return cottageReservation;
	}
	
	public static Page<CottageQuickReservationDTO> pageCottageQuickReservationToPageCottageQuickReservationDTO(
			Page<CottageQuickReservation> cottageReservationPages) {
			Page<CottageQuickReservationDTO> cottageReservationDTOPages = cottageReservationPages.map(new Function<CottageQuickReservation, CottageQuickReservationDTO>() {
				@Override
				public CottageQuickReservationDTO apply(CottageQuickReservation entity) {
					CottageQuickReservationDTO dto = CottageQuickReservationMapper.CottageQuickReservationToCottageQuickReservationDTO(entity);
					return dto;
				}
			});
			return cottageReservationDTOPages;
	}

}
