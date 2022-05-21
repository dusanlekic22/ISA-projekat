package isaproject.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CottageReservationDTO;
import isaproject.mapper.CottageReservationMapper;
import isaproject.model.Cottage;
import isaproject.model.CottageQuickReservation;
import isaproject.model.CottageReservation;
import isaproject.model.DateSpan;
import isaproject.repository.CottageQuickReservationRepository;
import isaproject.repository.CottageRepository;
import isaproject.repository.CottageReservationRepository;
import isaproject.service.CottageReservationService;
import isaproject.service.CustomerService;

@Service
public class CottageReservationServiceImpl implements CottageReservationService {

	@Autowired
	CottageReservationRepository cottageReservationRepository;
	@Autowired
	CottageQuickReservationRepository cottageQuickReservationRepository;
	@Autowired
	CottageRepository cottageRepository;
	@Autowired
	CustomerService customerService;

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
		cottageReservation.setConfirmed(true);
		
		if(!cottageReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}

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
		return (existingReservation.getCustomer().getId() == newReservation.getCustomer().getId())
				&& existingReservation.getDuration().isBetween(LocalDateTime.now());
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
	public CottageReservationDTO reserveCottageOwner(CottageReservationDTO cottageReservationDTO, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		CottageReservation cottageReservation = CottageReservationMapper
				.CottageReservationDTOToCottageReservation(cottageReservationDTO);
		cottageReservation.setConfirmed(false);
		
		if(!cottageReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}
		
		boolean inAction = false;
		for (CottageReservation q : cottageReservationRepository
				.findByCottageId(cottageReservation.getCottage().getId())) {

			if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
				System.out.println("izaso1");
				return null;
			}

			if (isCustomersReservationInAction(cottageReservation, q)) {
				inAction = true;
			}
		}

		for (CottageQuickReservation q : cottageQuickReservationRepository
				.findByCottageId(cottageReservation.getCottage().getId())) {

			if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
				System.out.println("izaso12");
				return null;
			}
		}

		if (!inAction) {
			System.out.println("izaso3");
			return null;
		}
		
		for (DateSpan dateSpan : cottageReservation.getCottage().getAvailableReservationDateSpan()) {
			if (cottageReservation.getDuration().overlapsWith(dateSpan)) {
				reserveAvailableDateSpan(cottageReservation, dateSpan);
				break;
			}
		}
		CottageReservation cottageQuickReservationReturn = cottageReservationRepository.save(cottageReservation);
		
		customerService.sendReservationConfirmationEmail(siteUrl, cottageQuickReservationReturn);

		return CottageReservationMapper
				.CottageReservationToCottageReservationDTO(cottageQuickReservationReturn);
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
		CottageReservation cottageReservation = cottageReservationRepository.findById(id).get();
		freeReservedSpan(cottageReservation);
		cottageQuickReservationRepository.deleteById(id);
		return CottageReservationMapper
				.CottageReservationToCottageReservationDTO(cottageReservation);
	}
	
	private void freeReservedSpan(CottageReservation cottageReservation) {
		Cottage cottage = cottageReservation.getCottage();
		DateSpan duration = cottageReservation.getDuration();
		DateSpan newAvailableDateSpan = new DateSpan(duration);
		Set<DateSpan> availableDateSpans = new HashSet<>(cottage.getAvailableReservationDateSpan());
		boolean startChanged = false;
		boolean endChanged = false;
		for (DateSpan dateSpan : availableDateSpans) {
			if (newAvailableDateSpan.getStartDate().compareTo(dateSpan.getEndDate()) == 0) {
				cottage.getAvailableReservationDateSpan().remove(dateSpan);
				if (endChanged) {
					cottage.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateSpan(dateSpan.getStartDate(), newAvailableDateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateSpan(dateSpan.getStartDate(), duration.getEndDate());
					startChanged = true;
				}
				cottage.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
			if (newAvailableDateSpan.getEndDate().compareTo(dateSpan.getStartDate()) == 0) {
				cottage.getAvailableReservationDateSpan().remove(dateSpan);
				if (startChanged) {
					cottage.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateSpan(newAvailableDateSpan.getStartDate(), dateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateSpan(duration.getStartDate(), dateSpan.getEndDate());
					endChanged = true;
				}
				cottage.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
		}

		cottageRepository.save(cottage);
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

	@Override
	public CottageReservationDTO confirmReservation(Long id) {
		CottageReservation cottageReservation = cottageReservationRepository.findById(id).get();
		cottageReservation.setConfirmed(true);
		return CottageReservationMapper.CottageReservationToCottageReservationDTO(cottageReservationRepository.save(cottageReservation));
	}

}
