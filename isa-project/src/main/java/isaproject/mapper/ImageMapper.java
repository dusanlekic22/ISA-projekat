package isaproject.mapper;

import isaproject.dto.CottageImageDTO;
import isaproject.dto.FishingImageDTO;
import isaproject.model.CottageImage;
import isaproject.model.FishingImage;

public class ImageMapper {
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
	
	public static FishingImage FishingImageDTOToFishingImage(FishingImageDTO fishingImageDTO) {
		FishingImage fishingImage = new FishingImage();
		fishingImage.setId(fishingImageDTO.getId());
		fishingImage.setImage(fishingImageDTO.getImage());
		fishingImage.setFishingCourse(fishingImageDTO.getFishingCourse());
		return fishingImage;
	}
	
	public static FishingImageDTO FishingImageToFishingImageDTO(FishingImage fishingImage) {
		FishingImageDTO fishingImageDTO = new FishingImageDTO();
		fishingImageDTO.setId(fishingImage.getId());
		fishingImageDTO.setImage(fishingImage.getImage());
		fishingImageDTO.setFishingCourse(fishingImage.getFishingCourse());
		return fishingImageDTO;
	}
	
}
