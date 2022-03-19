package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CottageDTO;
import isaproject.mapper.CottageMapper;
import isaproject.model.Cottage;
import isaproject.model.CottageImage;
import isaproject.repository.CottageRepository;
import isaproject.service.CottageService;

@Service
public class CottageServiceImpl implements CottageService {
	
	@Autowired
	private CottageRepository cottageRepository;
	
	@Transactional
	public CottageDTO findById(Long id) {
		Cottage cottage = cottageRepository.getById(id);
		return CottageMapper.CottageToCottageDTO(cottage);
	}

	public Set<CottageDTO> findAll(){
		Set<Cottage> cottages = new HashSet<>(cottageRepository.findAll()) ;
	        Set<CottageDTO> dtos = new HashSet<>();
	        if(cottages.size()!=0){
	            CottageDTO dto;
	            for(Cottage p : cottages){
	                dto = CottageMapper.CottageToCottageDTO(p);
	                dtos.add(dto);
	            }
	        }
	        return dtos;
	}
	
	public void remove(Long id) {
		cottageRepository.deleteById(id);
	}
	
	public CottageDTO save(CottageDTO cottageDTO) {
		Cottage cottage = CottageMapper.CottageDTOToCottage(cottageDTO);
		return CottageMapper.CottageToCottageDTO(cottageRepository.save(cottage));
	}


	@Override
	public Set<CottageDTO> findByCottageName(String name) {
		Set<Cottage> cottages = cottageRepository.findByName(name);
        Set<CottageDTO> dtos = new HashSet<>();
        System.out.println(name);
        if(cottages.size()!=0){
        	
            CottageDTO dto;
            for(Cottage p : cottages){
                dto = CottageMapper.CottageToCottageDTO(p);
                dtos.add(dto);
            }
        }

        return dtos;
	}
	
}
