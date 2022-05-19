package isaproject.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CottageQuickReservationDTO;
import isaproject.dto.CottageReservationDTO;
import isaproject.mapper.CottageQuickReservationMapper;
import isaproject.mapper.CottageReservationMapper;
import isaproject.model.CottageQuickReservation;
import isaproject.model.CottageReservation;
import isaproject.model.Customer;
import isaproject.repository.CottageQuickReservationRepository;
import isaproject.repository.CottageReservationRepository;
import isaproject.service.CottageQuickReservationService;
import isaproject.service.CustomerService;

@Service
public class CottageQuickReservationServiceImpl implements CottageQuickReservationService {

	@Autowired
	CottageQuickReservationRepository cottageQuickReservationRepository;

	@Autowired
	CottageReservationRepository cottageReservationRepository;
	
	@Autowired
	CustomerService customerService;

	
	@Override
	public CottageQuickReservationDTO findById(Long id) {
		CottageQuickReservation cottageQuickReservation = cottageQuickReservationRepository.findById(id).orElse(null);
		return CottageQuickReservationMapper
				.CottageQuickReservationToCottageQuickReservationDTO(cottageQuickReservation);
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
		if (cottages.size() != 0) {
			CottageQuickReservationDTO dto;
			for (CottageQuickReservation p : cottages) {
				dto = CottageQuickReservationMapper.CottageQuickReservationToCottageQuickReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}
	
	@Transactional
	@Override
	public CottageQuickReservationDTO save(CottageQuickReservationDTO cottageQuickReservationDTO)
			throws UnsupportedEncodingException, MessagingException {
		CottageQuickReservation cottageQuickReservation = CottageQuickReservationMapper
				.CottageQuickReservationDTOToCottageQuickReservation(cottageQuickReservationDTO);
		for (CottageQuickReservation q : cottageQuickReservationRepository
				.findByCottageId(cottageQuickReservation.getCottage().getId())) {
			if (q.getDuration().overlapsWith(cottageQuickReservation.getDuration())) {
				return null;
			}
		}

		for(Customer customer:cottageQuickReservation.getCottage().getSubscribers()) {
			customerService.sendNewQuickReservationEmail(customer, null, cottageQuickReservation);
		}
		
		return CottageQuickReservationMapper.CottageQuickReservationToCottageQuickReservationDTO(
				cottageQuickReservationRepository.save(cottageQuickReservation));
	}

	@Override
	public Set<CottageQuickReservationDTO> findByCottageId(Long id) {
		Set<CottageQuickReservation> cottageQuickReservations = new HashSet<>(
				cottageQuickReservationRepository.findByCottageId(id));
		Set<CottageQuickReservationDTO> dtos = new HashSet<>();
		if (cottageQuickReservations.size() != 0) {

			CottageQuickReservationDTO dto;
			for (CottageQuickReservation p : cottageQuickReservations) {
				dto = CottageQuickReservationMapper.CottageQuickReservationToCottageQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Transactional
	@Override
	public CottageQuickReservationDTO deleteById(Long id) {
		CottageQuickReservationDTO cottageQuickReservationDTO = findById(id);
		cottageQuickReservationRepository.deleteById(id);
		return cottageQuickReservationDTO;
	}

	@Transactional
	@Override
	public CottageReservationDTO appointQuickReservation(CottageReservationDTO cottageReservationDTO) {		
		System.out.println(cottageReservationDTO.getId());
		deleteById(cottageReservationDTO.getId());
		CottageReservation cottageReservation = CottageReservationMapper.CottageReservationDTOToCottageReservation(cottageReservationDTO);
		CottageReservation cottageReservationReturn = cottageReservationRepository.save(cottageReservation);
		return CottageReservationMapper.CottageReservationToCottageReservationDTO(cottageReservationReturn);
	}

}
