package isaproject.mapper;

import isaproject.dto.AdditionalServiceDTO;
import isaproject.model.AdditionalService;

public class AdditionalServiceMapper {
	
	public static AdditionalService AdditionalServiceDTOToAdditionalService(AdditionalServiceDTO additionalServiceDTO) {
		AdditionalService additionalService = new AdditionalService();
		additionalService.setId(additionalServiceDTO.getId());
		additionalService.setName(additionalServiceDTO.getName());
		additionalService.setPrice(additionalServiceDTO.getPrice());
		additionalService.setCottage(additionalServiceDTO.getCottage());
		return additionalService;
	}
	
	public static AdditionalServiceDTO AdditionalServiceToAdditionalServiceDTO(AdditionalService additionalService) {
		AdditionalServiceDTO additionalServiceDTO = new AdditionalServiceDTO();
		additionalServiceDTO.setId(additionalService.getId());
		additionalServiceDTO.setName(additionalService.getName());
		additionalServiceDTO.setPrice(additionalService.getPrice());
		additionalServiceDTO.setCottage(additionalService.getCottage());
		return additionalServiceDTO;
	}

}
