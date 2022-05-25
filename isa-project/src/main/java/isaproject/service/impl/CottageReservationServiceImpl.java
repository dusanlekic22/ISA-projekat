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
import isaproject.dto.CustomerDTO;
import isaproject.mapper.CottageReservationMapper;
import isaproject.mapper.CustomerMapper;
import isaproject.model.Cottage;
import isaproject.model.CottageQuickReservation;
import isaproject.model.CottageReservation;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.ReservationDateSpanWithPrice;
import isaproject.repository.CottageQuickReservationRepository;
import isaproject.repository.CottageRepository;
import isaproject.repository.CottageReservationRepository;
import isaproject.repository.CustomerRepository;
import isaproject.service.CottageReservationService;
import isaproject.service.CustomerService;

@Service
public class CottageReservationServiceImpl implements CottageReservationService {

	CottageReservationRepository cottageReservationRepository;
	CottageQuickReservationRepository cottageQuickReservationRepository;
	CottageRepository cottageRepository;
	CustomerService customerService;
	CustomerRepository customerRepository;

	@Autowired
	public CottageReservationServiceImpl(CottageReservationRepository cottageReservationRepository,
			CottageQuickReservationRepository cottageQuickReservationRepository, CottageRepository cottageRepository,
			CustomerService customerService, CustomerRepository customerRepository) {
		super();
		this.cottageReservationRepository = cottageReservationRepository;
		this.cottageQuickReservationRepository = cottageQuickReservationRepository;
		this.cottageRepository = cottageRepository;
		this.customerService = customerService;
		this.customerRepository = customerRepository;
	}

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

	@Override
	public Set<CustomerDTO> findCustomersHasCurrentReservation() {
		Set<Customer> customers = new HashSet<>(customerRepository.findAll());
		Set<CustomerDTO> dtos = new HashSet<>();
		if (customers.size() != 0) {
			CustomerDTO dto;
			for (Customer p : customers) {
				for (CottageReservation cottageReservation : p.getCottageReservation()) {
					if (cottageReservation.getDuration().isBetween(LocalDateTime.now())) {
						dto = CustomerMapper.customertoCustomerDTO(p);
						dtos.add(dto);
						break;
					}
				}
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
		cottageReservation.setCustomer(customerRepository.findById(cottageReservationDTO.getCustomer().getId()).get());
		if (!cottageReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
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

		for (ReservationDateSpanWithPrice dateTimeSpan : cottageReservation.getCottage().getAvailableReservationDateSpanWithPrice()) {
			if (cottageReservation.getDuration().overlapsWith(dateTimeSpan.getDateSpan())) {
				overlaps = true;
				reserveAvailableDateSpan(cottageReservation, dateTimeSpan);
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

	private void reserveAvailableDateSpan(CottageReservation cottageReservation, ReservationDateSpanWithPrice availableDateSpan) {
		Cottage cottage = cottageReservation.getCottage();
		DateTimeSpan duration = cottageReservation.getDuration();
		cottage.getAvailableReservationDateSpanWithPrice().remove( availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getDateSpan().getStartDate()) == 0
				&& duration.getEndDate().compareTo(availableDateSpan.getDateSpan().getEndDate()) <= 0) {
			ReservationDateSpanWithPrice newDateSpan = new ReservationDateSpanWithPrice(new DateTimeSpan(duration.getEndDate(), availableDateSpan.getDateSpan().getEndDate()),33);
			cottage.getAvailableReservationDateSpanWithPrice().add( newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getDateSpan().getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getDateSpan().getEndDate()) == 0) {
			ReservationDateSpanWithPrice newDateSpan = new ReservationDateSpanWithPrice(new DateTimeSpan(availableDateSpan.getDateSpan().getStartDate(), duration.getStartDate()),33);
		
			cottage.getAvailableReservationDateSpanWithPrice().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getDateSpan().getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getDateSpan().getEndDate()) <= 0) {
			ReservationDateSpanWithPrice newDateSpan1 = new ReservationDateSpanWithPrice(new DateTimeSpan(availableDateSpan.getDateSpan().getStartDate(), duration.getStartDate()),33);
			ReservationDateSpanWithPrice newDateSpan2 = new ReservationDateSpanWithPrice(new DateTimeSpan(duration.getEndDate(), availableDateSpan.getDateSpan().getEndDate()),33);
		
			cottage.getAvailableReservationDateSpanWithPrice().add(newDateSpan1);
			cottage.getAvailableReservationDateSpanWithPrice().add(newDateSpan2);
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
		cottageReservation.setCustomer(customerRepository.findById(cottageReservationDTO.getCustomer().getId()).get());
		if (!cottageReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}

		boolean inAction = false;
		for (CottageReservation q : cottageReservationRepository
				.findByCottageId(cottageReservation.getCottage().getId())) {

			if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
				return null;
			}

			if (isCustomersReservationInAction(cottageReservation, q)) {
				inAction = true;
			}
		}

		for (CottageQuickReservation q : cottageQuickReservationRepository
				.findByCottageId(cottageReservation.getCottage().getId())) {

			if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
				return null;
			}
		}

		if (!inAction) {
			return null;
		}
		
		for (ReservationDateSpanWithPrice dateTimeSpan : cottageReservation.getCottage().getAvailableReservationDateSpanWithPrice()) {
			if (cottageReservation.getDuration().overlapsWith(dateTimeSpan.getDateSpan())) {
				reserveAvailableDateSpan(cottageReservation, dateTimeSpan);
				break;
			}
		}
		CottageReservation cottageReservationReturn = cottageReservationRepository.save(cottageReservation);

		customerService.sendReservationConfirmationEmail(siteUrl, cottageReservationReturn);

		return CottageReservationMapper.CottageReservationToCottageReservationDTO(cottageReservationReturn);
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
		return CottageReservationMapper.CottageReservationToCottageReservationDTO(cottageReservation);
	}

	private void freeReservedSpan(CottageReservation cottageReservation) {
		Cottage cottage = cottageReservation.getCottage();
		DateTimeSpan duration = cottageReservation.getDuration();
		DateTimeSpan newAvailableDateSpan = new DateTimeSpan(duration);
		Set<ReservationDateSpanWithPrice> availableDateSpans = new HashSet<>(cottage.getAvailableReservationDateSpanWithPrice());
		boolean startChanged = false;
		boolean endChanged = false;
		for (ReservationDateSpanWithPrice dateTimeSpan : availableDateSpans) {
			if (newAvailableDateSpan.getStartDate().compareTo(dateTimeSpan.getDateSpan().getEndDate()) == 0) {
				cottage.getAvailableReservationDateSpanWithPrice().remove(dateTimeSpan);
				if (endChanged) {
					cottage.getAvailableReservationDateSpanWithPrice().remove( new ReservationDateSpanWithPrice( newAvailableDateSpan,33));
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getDateSpan().getStartDate(), newAvailableDateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getDateSpan().getStartDate(), duration.getEndDate());
					startChanged = true;
				}
				cottage.getAvailableReservationDateSpanWithPrice().add( new ReservationDateSpanWithPrice(newAvailableDateSpan,33));
			}
			if (newAvailableDateSpan.getEndDate().compareTo(dateTimeSpan.getDateSpan().getStartDate()) == 0) {
				cottage.getAvailableReservationDateSpanWithPrice().remove(dateTimeSpan);
				if (startChanged) {
					cottage.getAvailableReservationDateSpanWithPrice().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(newAvailableDateSpan.getStartDate(), dateTimeSpan.getDateSpan().getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(duration.getStartDate(), dateTimeSpan.getDateSpan().getEndDate());
					endChanged = true;
				}
				cottage.getAvailableReservationDateSpanWithPrice().add( new ReservationDateSpanWithPrice(newAvailableDateSpan,33));
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
		return CottageReservationMapper
				.CottageReservationToCottageReservationDTO(cottageReservationRepository.save(cottageReservation));
	}

}
