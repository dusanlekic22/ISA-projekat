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
public class AdditionalServiceServiceImpl implements AdditionalServiceService {

	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;

	public AdditionalServiceDTO findById(Long id) {
		AdditionalService additionalService = additionalServiceRepository.getById(id);
		return AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(additionalService);
	}

	@Transactional
	@Override
	public AdditionalServiceDTO save(AdditionalServiceDTO additionalServiceDTO) {
		AdditionalService additionalService = AdditionalServiceMapper
				.AdditionalServiceDTOToAdditionalService(additionalServiceDTO);
		return AdditionalServiceMapper
				.AdditionalServiceToAdditionalServiceDTO(additionalServiceRepository.save(additionalService));
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
		if (allAdditionalServices.size() != 0) {

			AdditionalServiceDTO dto = new AdditionalServiceDTO();
			;
			for (AdditionalService p : allAdditionalServices) {
				dto = AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<AdditionalServiceDTO> findByCottageId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByCottageId(id));
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
	public Set<AdditionalServiceDTO> findByBoatId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByBoatId(id));
		Set<AdditionalServiceDTO> dtos = new HashSet<>();
		if (allAdditionalServices.size() != 0) {

			AdditionalServiceDTO dto = new AdditionalServiceDTO();
			;
			for (AdditionalService p : allAdditionalServices) {
				dto = AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<AdditionalServiceDTO> findByFishingCourseId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(
				additionalServiceRepository.findByFishingCourseId(id));
		Set<AdditionalServiceDTO> dtos = new HashSet<>();
		for (AdditionalService service : allAdditionalServices) {
			dtos.add(AdditionalServiceMapper.AdditionalServiceToAdditionalServiceDTO(service));
		}
		return dtos;
	}

	@Override
	public Set<AdditionalServiceDTO> findByCottageReservationId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByCottageReservationId(id));
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
	public Set<AdditionalServiceDTO> findByCottageQuickReservationId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByCottageQuickReservationId(id));
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
	public Set<AdditionalServiceDTO> findByBoatReservationId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByBoatReservationId(id));
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
	public Set<AdditionalServiceDTO> findByBoatQuickReservationId(Long id) {
		Set<AdditionalService> allAdditionalServices = new HashSet<>(additionalServiceRepository.findByBoatQuickReservationId(id));
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
