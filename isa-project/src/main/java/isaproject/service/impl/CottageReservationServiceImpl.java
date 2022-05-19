package isaproject.service.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CottageReservationDTO;
import isaproject.mapper.CottageReservationMapper;
import isaproject.model.Cottage;
import isaproject.model.CottageReservation;
import isaproject.model.DateSpan;
import isaproject.repository.CottageRepository;
import isaproject.repository.CottageReservationRepository;
import isaproject.service.CottageReservationService;

@Service
public class CottageReservationServiceImpl implements CottageReservationService {

	@Autowired
	CottageReservationRepository cottageReservationRepository;
	@Autowired
	CottageRepository cottageRepository;

	@Override
	public CottageReservationDTO findById(Long id) {
		CottageReservation cottageReservation = cottageReservationRepository.findById(id).orElse(null);
		return CottageReservationMapper.CottageReservationToCottageReservationDTO(cottageReservation);
	}

	@Override
	public Set<CottageReservationDTO> findByCottageName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<CottageReservationDTO> findAll() {
		Set<CottageReservation> cottages = new HashSet<>(cottageReservationRepository.findAll());
		Set<CottageReservationDTO> dtos = new HashSet<>();
		if (cottages.size() != 0) {
			CottageReservationDTO dto;
			for (CottageReservation p : cottages) {
				dto = CottageReservationMapper.CottageReservationToCottageReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Transactional
	@Override
	public CottageReservationDTO reserveCustomer(CottageReservationDTO cottageReservationDTO) {
		CottageReservation cottageReservation = CottageReservationMapper
				.CottageReservationDTOToCottageReservation(cottageReservationDTO);

		for (CottageReservation q : cottageReservationRepository
				.findByCottageId(cottageReservation.getCottage().getId())) {
			if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
				return null;
			}
		}

		boolean overlaps = false;

		// Cottage cottage =
		// cottageRepository.getById(cottageReservation.getCottage().getId());

		for (DateSpan dateSpan : cottageReservation.getCottage().getAvailableReservationDateSpan()) {
			if (cottageReservation.getDuration().overlapsWith(dateSpan)) {
				overlaps = true;
				reserveAvailableDateSpan(cottageReservation, dateSpan);
				break;
			}
		}

		if (!overlaps)
			return null;

		return CottageReservationMapper
				.CottageReservationToCottageReservationDTO(cottageReservationRepository.save(cottageReservation));
	}

	private boolean isCustomersReservationInAction(CottageReservation newReservation,
			CottageReservation existingReservation) {
		System.out.println("AAAAAAAAAAAAAAAAA" + existingReservation.getDuration().isBetween(LocalDate.now()) + " " + LocalDate.now()+" " +existingReservation.getDuration().getStartDate()+" " +existingReservation.getDuration().getEndDate());
		return (existingReservation.getCustomer().getId() == newReservation.getCustomer().getId())
				&& existingReservation.getDuration().isBetween(LocalDate.now());
	}

	private void reserveAvailableDateSpan(CottageReservation cottageReservation, DateSpan availableDateSpan) {
		Cottage cottage = cottageReservation.getCottage();
		DateSpan duration = cottageReservation.getDuration();
		cottage.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) == 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateSpan newDateSpan = new DateSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			cottage.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) == 0) {
			DateSpan newDateSpan = new DateSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			cottage.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateSpan newDateSpan1 = new DateSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateSpan newDateSpan2 = new DateSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			cottage.getAvailableReservationDateSpan().add(newDateSpan1);
			cottage.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		cottageRepository.save(cottage);
	}

	@Transactional
	@Override
	public CottageReservationDTO reserveCottageOwner(CottageReservationDTO cottageReservationDTO) {
		CottageReservation cottageReservation = CottageReservationMapper
				.CottageReservationDTOToCottageReservation(cottageReservationDTO);
		boolean inAction = false;
		for (CottageReservation q : cottageReservationRepository
				.findByCottageId(cottageReservation.getCottage().getId())) {
			
			if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
				System.out.println("overlapped");
				return null;
			}
			
			if(isCustomersReservationInAction(cottageReservation, q)) {
				System.out.println("inaction");
				inAction = true;
			}
		}
		
		if(!inAction)
			return null;

		for (DateSpan dateSpan : cottageReservation.getCottage().getAvailableReservationDateSpan()) {
			if (cottageReservation.getDuration().overlapsWith(dateSpan)) {
				System.out.println("reserved");
				reserveAvailableDateSpan(cottageReservation, dateSpan);
				break;
			}
		}

		return CottageReservationMapper
				.CottageReservationToCottageReservationDTO(cottageReservationRepository.save(cottageReservation));
	}

	@Transactional
	@Override
	public Set<CottageReservationDTO> findByCottageId(Long id) {
		Set<CottageReservation> cottageReservations = new HashSet<>(cottageReservationRepository.findByCottageId(id));
		Set<CottageReservationDTO> dtos = new HashSet<>();
		if (cottageReservations.size() != 0) {

			CottageReservationDTO dto;
			for (CottageReservation p : cottageReservations) {
				dto = CottageReservationMapper.CottageReservationToCottageReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Transactional
	@Override
	public CottageReservationDTO deleteById(Long id) {
		CottageReservationDTO cottageReservationDTO = findById(id);
		cottageReservationRepository.deleteById(id);
		return cottageReservationDTO;
	}

	@Override
	public Set<CottageReservationDTO> findAllPast() {
		Set<CottageReservationDTO> dtos = new HashSet<>();
		for (CottageReservationDTO cottageReservationDTO : findAll()) {
			if (cottageReservationDTO.getDuration().passed())
				dtos.add(cottageReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<CottageReservationDTO> findAllActive() {
		Set<CottageReservationDTO> dtos = new HashSet<>();
		for (CottageReservationDTO cottageReservationDTO : findAll()) {
			if (!cottageReservationDTO.getDuration().passed())
				dtos.add(cottageReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<CottageReservationDTO> findAllPastByCottageId(Long id) {
		Set<CottageReservationDTO> dtos = new HashSet<>();
		for (CottageReservationDTO cottageReservationDTO : findByCottageId(id)) {
			if (cottageReservationDTO.getDuration().passed())
				dtos.add(cottageReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<CottageReservationDTO> findAllActiveByCottageId(Long id) {
		Set<CottageReservationDTO> dtos = new HashSet<>();
		for (CottageReservationDTO cottageReservationDTO : findByCottageId(id)) {
			if (!cottageReservationDTO.getDuration().passed())
				dtos.add(cottageReservationDTO);
		}
		return dtos;
	}

}
