package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.CottageImageDTO;
import isaproject.mapper.CottageImageMapper;
import isaproject.model.CottageImage;
import isaproject.repository.CottageImageRepository;
import isaproject.service.CottageImageService;

@Service
public class CottageImageServiceImpl implements CottageImageService{

	@Autowired
	private CottageImageRepository cottageImageRepository;

	@Override
	public CottageImageDTO save(CottageImageDTO cottageImageDTO) {
		CottageImage cottageImage = CottageImageMapper.CottageImageDTOToCottageImage(cottageImageDTO);
		return CottageImageMapper.CottageImageToCottageImageDTO(cottageImageRepository.save(cottageImage));
	}

}
