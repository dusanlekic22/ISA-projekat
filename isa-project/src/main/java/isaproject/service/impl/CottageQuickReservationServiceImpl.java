package isaproject.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
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
import isaproject.model.Cottage;
import isaproject.model.CottageQuickReservation;
import isaproject.model.CottageReservation;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.repository.CottageQuickReservationRepository;
import isaproject.repository.CottageRepository;
import isaproject.repository.CottageReservationRepository;
import isaproject.repository.CustomerRepository;
import isaproject.service.CottageQuickReservationService;
import isaproject.service.CustomerService;

@Service
public class CottageQuickReservationServiceImpl implements CottageQuickReservationService {

	@Autowired
	CottageQuickReservationRepository cottageQuickReservationRepository;

	@Autowired
	CottageReservationRepository cottageReservationRepository;

	@Autowired
	CottageRepository cottageRepository;

	@Autowired
	CustomerRepository customerRepository;

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

	private void reserveAvailableDateSpan(CottageQuickReservation cottageQuickReservation, DateTimeSpan availableDateSpan) {
		Cottage cottage = cottageQuickReservation.getCottage();
		DateTimeSpan duration = cottageQuickReservation.getDuration();
		cottage.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			cottage.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			cottage.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			cottage.getAvailableReservationDateSpan().add(newDateSpan1);
			cottage.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		cottageRepository.save(cottage);
	}

	@Transactional
	@Override
	public CottageQuickReservationDTO save(CottageQuickReservationDTO cottageQuickReservationDTO, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		CottageQuickReservation cottageQuickReservation = CottageQuickReservationMapper
				.CottageQuickReservationDTOToCottageQuickReservation(cottageQuickReservationDTO);
		cottageQuickReservation.setReserved(false);

		if (!cottageQuickReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}

		for (CottageQuickReservation q : cottageQuickReservationRepository
				.findByCottageId(cottageQuickReservation.getCottage().getId())) {
			if (q.getDuration().overlapsWith(cottageQuickReservation.getDuration())) {
				return null;
			}
		}

		for (CottageReservation q : cottageReservationRepository
				.findByCottageId(cottageQuickReservation.getCottage().getId())) {
			if (q.getDuration().overlapsWith(cottageQuickReservation.getDuration())) {
				return null;
			}
		}

		for (DateTimeSpan dateTimeSpan : cottageQuickReservation.getCottage().getAvailableReservationDateSpan()) {

			if (cottageQuickReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpan(cottageQuickReservation, dateTimeSpan);
				break;
			}
		}
		CottageQuickReservation cottageQuickReservationReturn = cottageQuickReservationRepository
				.save(cottageQuickReservation);
		for (Customer customer : cottageQuickReservation.getCottage().getSubscribers()) {
			System.out.println(customer.getFirstName());
			customerService.sendNewQuickReservationEmail(customer, siteUrl, cottageQuickReservationReturn);
		}

		return CottageQuickReservationMapper
				.CottageQuickReservationToCottageQuickReservationDTO(cottageQuickReservationReturn);
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
		CottageQuickReservation cottageQuickReservation = cottageQuickReservationRepository.findById(id).get();
		freeReservedSpan(cottageQuickReservation);
		cottageQuickReservationRepository.deleteById(id);
		return CottageQuickReservationMapper
				.CottageQuickReservationToCottageQuickReservationDTO(cottageQuickReservation);
	}

	private void freeReservedSpan(CottageQuickReservation cottageQuickReservation) {
		Cottage cottage = cottageQuickReservation.getCottage();
		DateTimeSpan duration = cottageQuickReservation.getDuration();
		DateTimeSpan newAvailableDateSpan = new DateTimeSpan(duration);
		Set<DateTimeSpan> availableDateSpans = new HashSet<>(cottage.getAvailableReservationDateSpan());
		boolean startChanged = false;
		boolean endChanged = false;
		for (DateTimeSpan dateTimeSpan : availableDateSpans) {
			if (newAvailableDateSpan.getStartDate().compareTo(dateTimeSpan.getEndDate()) == 0) {
				cottage.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (endChanged) {
					cottage.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), newAvailableDateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), duration.getEndDate());
					startChanged = true;
				}
				cottage.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
			if (newAvailableDateSpan.getEndDate().compareTo(dateTimeSpan.getStartDate()) == 0) {
				cottage.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (startChanged) {
					cottage.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(newAvailableDateSpan.getStartDate(), dateTimeSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(duration.getStartDate(), dateTimeSpan.getEndDate());
					endChanged = true;
				}
				cottage.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
		}

		cottageRepository.save(cottage);
	}

	@Transactional
	@Override
	public CottageReservationDTO appointQuickReservation(Long reservationId, Long userId) {
		CottageQuickReservation cottageQuickReservation = cottageQuickReservationRepository.findById(reservationId)
				.get();
		if (!cottageQuickReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}
		cottageQuickReservation.setReserved(true);
		cottageQuickReservationRepository.save(cottageQuickReservation);
		CottageReservation cottageReservation = CottageQuickReservationMapper
				.CottageQuickReservationToCottageReservation(cottageQuickReservation);
		cottageReservation.setCustomer(customerRepository.findById(userId).get());
		CottageReservation cottageReservationReturn = cottageReservationRepository.save(cottageReservation);
		return CottageReservationMapper.CottageReservationToCottageReservationDTO(cottageReservationReturn);
	}

	@Override
	public Set<CottageQuickReservationDTO> findByIsReservedFalseAndCottageId(Long id) {
		Set<CottageQuickReservation> cottageQuickReservations = new HashSet<>(
				cottageQuickReservationRepository.findByIsReservedFalseAndCottageId(id));
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

	@Override
	public Set<CottageQuickReservationDTO> findByIsReservedFalse() {
		Set<CottageQuickReservation> cottageQuickReservations = new HashSet<>(
				cottageQuickReservationRepository.findByIsReservedFalse());
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

}
