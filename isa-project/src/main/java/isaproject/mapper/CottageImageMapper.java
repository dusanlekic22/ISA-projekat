package isaproject.mapper;

import isaproject.dto.CottageImageDTO;
import isaproject.model.cottage.CottageImage;

public class CottageImageMapper {
	public static CottageImage CottageImageDTOToCottageImage(CottageImageDTO cottageImageDTO) {
		CottageImage cottageImage = new CottageImage();
		cottageImage.setId(cottageImageDTO.getId());
		cottageImage.setImage(cottageImageDTO.getImage());
		cottageImage.setCottage(cottageImageDTO.getCottage());
		return cottageImage;
	}
	
	public static CottageImageDTO CottageImageToCottageImageDTO(CottageImage cottageImage) {
		CottageImageDTO cottageImageDTO = new CottageImageDTO();
		cottageImageDTO.setId(cottageImage.getId());
		cottageImageDTO.setImage(cottageImage.getImage());
		cottageImageDTO.setCottage(cottageImage.getCottage());
		return cottageImageDTO;
	}
}
