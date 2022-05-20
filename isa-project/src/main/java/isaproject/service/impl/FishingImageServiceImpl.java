package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingImageDTO;
import isaproject.mapper.ImageMapper;
import isaproject.model.FishingImage;
import isaproject.repository.FishingImageRepository;
import isaproject.service.FishingImageService;

@Service
public class FishingImageServiceImpl implements FishingImageService {

	private FishingImageRepository fishingImageRepository;
	
	@Autowired
	public FishingImageServiceImpl(FishingImageRepository fishingImageRepository) {
		super();
		this.fishingImageRepository = fishingImageRepository;
	}

	@Override
	public FishingImageDTO findById(Long id) {
		FishingImage fishingImage = fishingImageRepository.getById(id);
		return ImageMapper.FishingImageToFishingImageDTO(fishingImage);
	}

	@Override
	public FishingImageDTO save(FishingImageDTO fishingImageDTO) {
		FishingImage fishingImage = ImageMapper.FishingImageDTOToFishingImage(fishingImageDTO);
		fishingImageRepository.save(fishingImage);
		return ImageMapper.FishingImageToFishingImageDTO(fishingImage);
	}

	@Override
	public FishingImageDTO deleteById(Long id) {
		FishingImageDTO fishingImageDTO = findById(id);
		fishingImageRepository.deleteById(id);
		return fishingImageDTO;
	}
	
}
