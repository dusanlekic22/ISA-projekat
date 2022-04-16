package isaproject.service;

import java.util.Set;

import isaproject.dto.AdditionalServiceDTO;

public interface AdditionalServiceService {
	AdditionalServiceDTO findById(Long id);
	AdditionalServiceDTO save(AdditionalServiceDTO additionalService);
	AdditionalServiceDTO deleteById(Long id);
	Set<AdditionalServiceDTO> findFree();
}
