package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.AdditionalServiceDTO;
import isaproject.dto.AdditionalServiceDTO;
import isaproject.mapper.AdditionalServiceMapper;
import isaproject.model.AdditionalService;
import isaproject.repository.AdditionalServiceRepository;
import isaproject.service.AdditionalServiceService;

@Service
public class AdditionalServiceServiceImpl implements AdditionalServiceService{

	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	
	@Transactional
	public AdditionalServiceDTO findById(Long id) {
		AdditionalService additionalService = additionalServiceRepository.getById(id);
		return AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(additionalService);
	}

	@Override
	public AdditionalServiceDTO save(AdditionalServiceDTO additionalServiceDTO) {
		AdditionalService additionalService = AdditionalServiceMapper.AdditionalServiceDTOToAdditionalService(additionalServiceDTO);
		return AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(additionalServiceRepository.save(additionalService));
	}

	@Transactional
	@Override
	public AdditionalServiceDTO deleteById(Long id) {
		AdditionalServiceDTO additionalServiceDTO = findById(id);
		additionalServiceRepository.deleteById(id);
		return additionalServiceDTO;
	}

}
