package isaproject.service;

import isaproject.dto.FishingImageDTO;

public interface FishingImageService {

	FishingImageDTO findById(Long id);
	FishingImageDTO save(FishingImageDTO fishingImageDTO);
	FishingImageDTO deleteById(Long id);
}
