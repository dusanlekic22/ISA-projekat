package isaproject.service;

import isaproject.dto.AdditionalServiceDTO;

public interface AdditionalServiceService {
	AdditionalServiceDTO findById(Long id);
	AdditionalServiceDTO save(AdditionalServiceDTO additionalService);
	AdditionalServiceDTO deleteById(Long id);
}
