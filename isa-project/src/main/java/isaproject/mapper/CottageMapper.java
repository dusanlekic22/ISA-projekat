package isaproject.mapper;

import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.cottage.CottageDTO;
import isaproject.model.cottage.Cottage;

public class CottageMapper {
	
	public static Cottage CottageDTOToCottage(CottageDTO cottageDTO) {
		Cottage cottage = new Cottage();
		cottage.setId(cottageDTO.getId());
		cottage.setName(cottageDTO.getName());
		cottage.setAddress(cottageDTO.getAddress());
		cottage.setPromoDescription(cottageDTO.getPromoDescription());
		cottage.setGrade(cottageDTO.getGrade());
		cottage.setBedCount(cottageDTO.getBedCount());
		cottage.setRoomCount(cottageDTO.getRoomCount());
		cottage.setPricePerHour(cottageDTO.getPricePerHour());
		cottage.setAdditionalService(cottageDTO.getAdditionalService());
		cottage.setCottageImage(cottageDTO.getCottageImage());
		cottage.setCottageOwner(cottageDTO.getCottageOwner());
		cottage.setCottageQuickReservation(cottageDTO.getCottageQuickReservation());
		cottage.setCottageReservation(cottageDTO.getCottageReservation());
		cottage.setCottageRules(cottageDTO.getCottageRules());
		cottage.setAvailableReservationDateSpan(cottageDTO.getAvailableReservationDateSpan());
		cottage.setCottageOwner(cottageDTO.getCottageOwner());
		cottage.setSubscribers(cottageDTO.getSubscribers());
		return cottage;
	}
	
	public static CottageDTO CottageToCottageDTO(Cottage cottage) {
		CottageDTO cottageDTO = new CottageDTO();
		cottageDTO.setId(cottage.getId());
		cottageDTO.setName(cottage.getName());
		cottageDTO.setAddress(cottage.getAddress());
		cottageDTO.setPromoDescription(cottage.getPromoDescription());
		cottageDTO.setGrade(cottage.getGrade());
		cottageDTO.setBedCount(cottage.getBedCount());
		cottageDTO.setRoomCount(cottage.getRoomCount());
		cottageDTO.setPricePerHour(cottage.getPricePerHour());
		cottageDTO.setAdditionalService(cottage.getAdditionalService());
		cottageDTO.setCottageImage(cottage.getCottageImage());
		cottageDTO.setCottageOwner(cottage.getCottageOwner());
		cottageDTO.setCottageQuickReservation(cottage.getCottageQuickReservation());
		cottageDTO.setCottageReservation(cottage.getCottageReservation());
		cottageDTO.setCottageRules(cottage.getCottageRules());
		cottageDTO.setAvailableReservationDateSpan(cottage.getAvailableReservationDateSpan());
		cottageDTO.setCottageOwner(cottage.getCottageOwner());
		cottageDTO.setSubscribers(cottage.getSubscribers());
		return cottageDTO;
	}
	
	public static CottageDTO CottageToCottageDTOWithPrice(Cottage cottage, int hours) {
		cottage.setPricePerHour(cottage.getPricePerHour()*hours);
		CottageDTO cottageDTO = CottageToCottageDTO(cottage);
		return cottageDTO;
	}
	
	public static Page<CottageDTO> pageCottageToPageCottageDTO(Page<Cottage> cottagePages) {
		Page<CottageDTO> cottageDTOPages = cottagePages.map(new Function<Cottage, CottageDTO>() {
			@Override
			public CottageDTO apply(Cottage entity) {
				CottageDTO dto = CottageMapper.CottageToCottageDTO(entity);
				return dto;
			}
		});
		return cottageDTOPages;
	}
	
}
