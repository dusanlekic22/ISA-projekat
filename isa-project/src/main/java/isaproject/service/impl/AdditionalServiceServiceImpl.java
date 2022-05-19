package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.AdditionalServiceDTO;
import isaproject.mapper.AdditionalServiceMapper;
import isaproject.model.AdditionalService;
import isaproject.repository.AdditionalServiceRepository;
import isaproject.service.AdditionalServiceService;

@Service
public class AdditionalServiceServiceImpl implements AdditionalServiceService{

	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	

	public AdditionalServiceDTO findById(Long id) {
		AdditionalService additionalService = additionalServiceRepository.getById(id);
		return AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(additionalService);
	}
	@Transactional
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
	
	@Override
	public Set<AdditionalServiceDTO> findFree() {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByCottageIsNull());
		Set<AdditionalServiceDTO> dtos = new HashSet<>();
	        if(allAdditionalServices.size()!=0){
	        	
	        	AdditionalServiceDTO dto = new AdditionalServiceDTO();;
	            for(AdditionalService p : allAdditionalServices){
	                dto = AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(p);
	                dtos.add(dto);
	            }
	        }

	     return dtos;
	}
	
	@Override
	public Set<AdditionalServiceDTO> findByCottageId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByCottageIdIs(id));
		Set<AdditionalServiceDTO> dtos = new HashSet<>();
        if(allAdditionalServices.size()!=0){
        	
        	AdditionalServiceDTO dto = new AdditionalServiceDTO();;
            for(AdditionalService p : allAdditionalServices){
                dto = AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(p);
                dtos.add(dto);
            }
        }

        return dtos;
	}

}
