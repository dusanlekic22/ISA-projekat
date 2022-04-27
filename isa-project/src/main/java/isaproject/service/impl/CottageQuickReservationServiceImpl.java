package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.CottageQuickReservationDTO;
import isaproject.mapper.CottageQuickReservationMapper;
import isaproject.model.CottageQuickReservation;
import isaproject.repository.CottageQuickReservationRepository;
import isaproject.service.CottageQuickReservationService;

@Service
public class CottageQuickReservationServiceImpl implements CottageQuickReservationService{

	@Autowired
	CottageQuickReservationRepository cottageQuickReservationRepository;
	
	@Override
	public CottageQuickReservationDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<CottageQuickReservationDTO> findByCottageName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<CottageQuickReservationDTO> findAll() {
		Set<CottageQuickReservation> cottages = new HashSet<>(cottageQuickReservationRepository.findAll());
        Set<CottageQuickReservationDTO> dtos = new HashSet<>();
        if(cottages.size()!=0){
        	CottageQuickReservationDTO dto;
            for(CottageQuickReservation p : cottages){
                dto = CottageQuickReservationMapper.CottageQuickReservationToCottageQuickReservationDTO(p);
                dtos.add(dto);
            }
        }
        return dtos;
	}

	@Override
	public CottageQuickReservationDTO save(CottageQuickReservationDTO cottageQuickReservationDTO) {
		CottageQuickReservation cottageQuickReservation = CottageQuickReservationMapper.CottageQuickReservationDTOToCottageQuickReservation(cottageQuickReservationDTO);
		for (CottageQuickReservation q : cottageQuickReservationRepository.findByCottageId(cottageQuickReservation.getCottage().getId())) {
			if(q.getDateSpan().overlapsWith(cottageQuickReservation.getDateSpan())) {
				return null;
			}
		}
		return CottageQuickReservationMapper.CottageQuickReservationToCottageQuickReservationDTO(cottageQuickReservationRepository.save(cottageQuickReservation));
	}

	@Override
	public Set<CottageQuickReservationDTO> findByCottageId(Long id) {
		Set<CottageQuickReservation> cottageQuickReservations = new HashSet<>(cottageQuickReservationRepository.findByCottageId(id));
        Set<CottageQuickReservationDTO> dtos = new HashSet<>();
        if(cottageQuickReservations.size()!=0){
        	
            CottageQuickReservationDTO dto;
            for(CottageQuickReservation p : cottageQuickReservations){
                dto = CottageQuickReservationMapper.CottageQuickReservationToCottageQuickReservationDTO(p);
                dtos.add(dto);
            }
        }

        return dtos;
	}

}
