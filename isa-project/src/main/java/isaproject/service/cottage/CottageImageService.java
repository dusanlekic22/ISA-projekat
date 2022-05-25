package isaproject.service.cottage;

import isaproject.dto.cottage.CottageImageDTO;

public interface CottageImageService {
	
	CottageImageDTO findById(Long id);
	CottageImageDTO save(CottageImageDTO cottageImage);
	CottageImageDTO deleteById(Long id);
}
