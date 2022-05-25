package isaproject.service.impl.cottage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.cottage.CottageImageDTO;
import isaproject.mapper.CottageImageMapper;
import isaproject.model.cottage.CottageImage;
import isaproject.repository.cottage.CottageImageRepository;
import isaproject.service.cottage.CottageImageService;

@Service
public class CottageImageServiceImpl implements CottageImageService {

	@Autowired
	private CottageImageRepository cottageImageRepository;


	public CottageImageDTO findById(Long id) {
		CottageImage cottageImage = cottageImageRepository.getById(id);
		return CottageImageMapper.CottageImageToCottageImageDTO(cottageImage);
	}

	@Transactional
	@Override
	public CottageImageDTO save(CottageImageDTO cottageImageDTO) {
		CottageImage cottageImage = CottageImageMapper.CottageImageDTOToCottageImage(cottageImageDTO);
		return CottageImageMapper.CottageImageToCottageImageDTO(cottageImageRepository.save(cottageImage));
	}

	@Transactional
	@Override
	public CottageImageDTO deleteById(Long id) {
		CottageImageDTO cottageImageDTO = findById(id);
		cottageImageRepository.deleteById(id);
		return cottageImageDTO;
	}
}
