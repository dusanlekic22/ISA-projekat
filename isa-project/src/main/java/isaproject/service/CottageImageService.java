package isaproject.service;

import isaproject.dto.CottageImageDTO;

public interface CottageImageService {
	

	CottageImageDTO findById(Long id);
	CottageImageDTO save(CottageImageDTO cottageImage);
	CottageImageDTO deleteById(Long id);
}
