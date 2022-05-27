package isaproject.service.impl.cottage;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CustomerDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.mapper.CottageReservationMapper;
import isaproject.mapper.CustomerMapper;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageQuickReservation;
import isaproject.model.cottage.CottageReservation;
import isaproject.repository.CustomerRepository;
import isaproject.repository.cottage.CottageQuickReservationRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.repository.cottage.CottageReservationRepository;
import isaproject.service.CustomerService;
import isaproject.service.cottage.CottageReservationService;

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
		long reservationPrice = cottageReservation.getCottage().getPricePerHour()*cottageReservation.getDuration().getHours();
		cottageReservation.setPrice((int) (long) reservationPrice);
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

		for (DateTimeSpan dateTimeSpan : cottageReservation.getCottage().getAvailableReservationDateSpan()) {
			if (cottageReservation.getDuration().overlapsWith(dateTimeSpan)) {
				overlaps = true;
				reserveAvailableDateSpanForCustomer(cottageReservation, dateTimeSpan);
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
	
	private void reserveAvailableDateSpanForOwner(CottageReservation cottageReservation, DateTimeSpan availableDateSpan) {
		Cottage cottage = cottageReservation.getCottage();
		DateTimeSpan duration = cottageReservation.getDuration();
		cottage.getAvailableReservationDateSpan().remove( availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			cottage.getAvailableReservationDateSpan().add( newDateSpan);
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

	private void reserveAvailableDateSpanForCustomer(CottageReservation cottageReservation, DateTimeSpan availableDateSpan) {
		Cottage cottage = cottageReservation.getCottage();
		DateTimeSpan duration = cottageReservation.getDuration();
		cottage.getAvailableReservationDateSpan().remove( availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) == 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			cottage.getAvailableReservationDateSpan().add( newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) == 0) {
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
		
		for (DateTimeSpan dateTimeSpan : cottageReservation.getCottage().getAvailableReservationDateSpan()) {
			if (cottageReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpanForOwner(cottageReservation, dateTimeSpan);
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
