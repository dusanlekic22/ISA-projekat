package isaproject.service.impl.cottage;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.hibernate.dialect.lock.PessimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CustomerDTO;
import isaproject.dto.LoyaltySettingsDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.mapper.CottageReservationMapper;
import isaproject.mapper.CustomerMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.model.AdditionalService;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.LoyaltyProgram;
import isaproject.model.LoyaltyRank;
import isaproject.model.SortType;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageOwner;
import isaproject.model.cottage.CottageQuickReservation;
import isaproject.model.cottage.CottageReservation;
import isaproject.repository.CustomerRepository;
import isaproject.repository.cottage.CottageOwnerRepository;
import isaproject.repository.cottage.CottageQuickReservationRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.repository.cottage.CottageReservationRepository;
import isaproject.service.CustomerService;
import isaproject.service.LoyaltySettingsService;
import isaproject.service.cottage.CottageReservationService;

@Service
public class CottageReservationServiceImpl implements CottageReservationService {

	CottageReservationRepository cottageReservationRepository;
	CottageQuickReservationRepository cottageQuickReservationRepository;
	CottageRepository cottageRepository;
	CustomerService customerService;
	CustomerRepository customerRepository;
	CottageOwnerRepository cottageOwnerRepository;
	LoyaltySettingsService loyaltySettingsService;

	@Autowired
	public CottageReservationServiceImpl(CottageReservationRepository cottageReservationRepository,
			CottageQuickReservationRepository cottageQuickReservationRepository, CottageRepository cottageRepository,
			CustomerService customerService, CustomerRepository customerRepository,
			CottageOwnerRepository cottageOwnerRepository, LoyaltySettingsService loyaltySettingsService) {
		super();
		this.cottageReservationRepository = cottageReservationRepository;
		this.cottageQuickReservationRepository = cottageQuickReservationRepository;
		this.cottageRepository = cottageRepository;
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.cottageOwnerRepository = cottageOwnerRepository;
		this.loyaltySettingsService = loyaltySettingsService;
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
	public Set<CustomerDTO> findCustomersHasCurrentReservation(long cottageId) {
		Set<Customer> customers = new HashSet<>(customerRepository.findAll());
		Set<CustomerDTO> dtos = new HashSet<>();
		if (customers.size() != 0) {
			CustomerDTO dto;
			for (Customer p : customers) {
				for (CottageReservation cottageReservation : p.getCottageReservation()) {
					if (cottageReservation.getDuration().isBetween(LocalDateTime.now())
							&& cottageReservation.getCottage().getId() == cottageId) {
						dto = CustomerMapper.customertoCustomerDTO(p);
						dtos.add(dto);
						break;
					}
				}
			}
		}
		return dtos;
	}

	@Override
	public Page<CottageReservationDTO> findAllPagination(Long id, SortTypeDTO sortTypeDTO, Pageable pageable) {

		SortType sortType = SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO);
		Sort sort = Sort.by("id").ascending();
		if (sortType != null && !sortType.getDirection().equalsIgnoreCase("")) {
			if (sortType.getDirection() != null && sortType.getDirection().toLowerCase().contains("desc")) {
				sort = Sort.by(sortType.getField()).descending();
			} else if (sortType.getDirection() != null && sortType.getDirection().toLowerCase().contains("asc")) {
				sort = Sort.by(sortType.getField()).ascending();
			}

			Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

			return CottageReservationMapper.pageCottageReservationToPageCottageReservationDTO(
					cottageReservationRepository.findCustomerReservationsSortByDuration(id, paging));
		} else {
			return CottageReservationMapper.pageCottageReservationToPageCottageReservationDTO(
					cottageReservationRepository.findCustomerReservationsSortByDuration(id, pageable));
		}
	}

	@Override
	public Page<CottageReservationDTO> findAllIncomingPagination(Long id, SortTypeDTO sortTypeDTO, Pageable pageable) {

		SortType sortType = SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO);
		Sort sort = Sort.by("id").ascending();
		if (sortType != null && !sortType.getDirection().equalsIgnoreCase("")) {
			if (sortType.getDirection() != null && sortType.getDirection().toLowerCase().contains("desc")) {
				sort = Sort.by(sortType.getField()).descending();
			} else if (sortType.getDirection() != null && sortType.getDirection().toLowerCase().contains("asc")) {
				sort = Sort.by(sortType.getField()).ascending();
			}
			Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

			return CottageReservationMapper.pageCottageReservationToPageCottageReservationDTO(
					cottageReservationRepository.findIncomingCustomerReservationsSortByDuration(id, paging));
		} else {
			return CottageReservationMapper.pageCottageReservationToPageCottageReservationDTO(
					cottageReservationRepository.findIncomingCustomerReservationsSortByDuration(id, pageable));
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CottageReservationDTO reserveCustomer(CottageReservationDTO cottageReservationDTO) {
		CottageReservation cottageReservation = CottageReservationMapper
				.CottageReservationDTOToCottageReservation(cottageReservationDTO);

		try {
			Cottage cottage = cottageRepository.getNotLockedCottage(cottageReservation.getCottage().getId());
			Customer customer = customerRepository.findById(cottageReservationDTO.getCustomer().getId()).get();
			if (this.customerService.isCustomerUnderPenalityRestrictions(customer.getId())) {
				return null;
			}
			CottageOwner owner = cottageOwnerRepository.findById(cottage.getCottageOwner().getId()).get();
			cottageReservation.setConfirmed(true);
			cottageReservation.setCustomer(customer);
			cottageReservation = calculateIncome(cottageReservation);
			customerService.promoteLoyaltyCustomer(customer);
			promoteLoyaltyCottageOwner(owner);

			if (cottageReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
				return null;
			}

			for (CottageReservation q : cottageReservationRepository
					.findByConfirmedIsTrueAndCottageId(cottageReservation.getCottage().getId())) {
				if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
					return null;
				}
			}

			for (DateTimeSpan dateTimeSpan : cottageReservation.getCottage().getUnavailableReservationDateSpan()) {
				if (dateTimeSpan.overlapsWith(cottageReservation.getDuration())) {
					return null;
				}
			}

			for (DateTimeSpan dateTimeSpan : cottageOwnerRepository
					.findById(cottageReservation.getCottage().getCottageOwner().getId()).get()
					.getUnavailableReservationDateSpan()) {
				if (dateTimeSpan.overlapsWith(cottageReservation.getDuration())) {
					return null;
				}
			}

			boolean overlaps = false;
			for (DateTimeSpan dateTimeSpan : cottageReservation.getCottage().getAvailableReservationDateSpan()) {
				if (cottageReservation.getDuration().overlapsWith(dateTimeSpan)) {
					overlaps = true;
					reserveAvailableDateSpan(cottageReservation, dateTimeSpan);
					break;
				}
			}

			if (!overlaps) {
				return null;
			}

			return CottageReservationMapper
					.CottageReservationToCottageReservationDTO(cottageReservationRepository.save(cottageReservation));

		} catch (PessimisticEntityLockException e) {
			e.printStackTrace();
			throw e;
		}

	}

	private void promoteLoyaltyCottageOwner(CottageOwner owner) {
		LoyaltySettingsDTO loyaltySettings = loyaltySettingsService.getLoyaltySettings();
		LoyaltyProgram loyaltyProgram = owner.getLoyaltyProgram();
		loyaltyProgram.setPoints(loyaltyProgram.getPoints() + loyaltySettings.getOwnerScore());
		if (loyaltyProgram.getPoints() > loyaltySettings.getMinScoreRegular())
			loyaltyProgram.setLoyaltyRank(LoyaltyRank.Regular);
		if (loyaltyProgram.getPoints() > loyaltySettings.getMinScoreSilver())
			loyaltyProgram.setLoyaltyRank(LoyaltyRank.Silver);
		if (loyaltyProgram.getPoints() > loyaltySettings.getMinScoreGold())
			loyaltyProgram.setLoyaltyRank(LoyaltyRank.Gold);
		owner.setLoyaltyProgram(loyaltyProgram);
		cottageOwnerRepository.save(owner);
	}

	private CottageReservation calculateIncome(CottageReservation cottageReservation) {
		LoyaltySettingsDTO loyaltySettings = loyaltySettingsService.getLoyaltySettings();
		Double cutomerDiscount = loyaltySettingsService
				.getCustomerDiscount(cottageReservation.getCustomer().getLoyaltyProgram());
		Double ownerRevenue = loyaltySettingsService
				.getOwnerRevenue(cottageReservation.getCottage().getCottageOwner().getLoyaltyProgram());
		Double siteRevenue = loyaltySettings.getSystemRevenue();
		Double cottagePrice = cottageReservation.getCottage().getPricePerHour()
				* cottageReservation.getDuration().getHours();
		Double reservationPrice = 0.0;
		Double reservationSiteIncome = 0.0;
		Double reservationIncome = 0.0;

		if (cottageReservation.getAdditionalService() != null) {
			for (AdditionalService additionalService : cottageReservation.getAdditionalService()) {
				cottagePrice += additionalService.getPrice();
			}
		}
		reservationPrice = cottagePrice - (cottagePrice * cutomerDiscount) / 100;
		reservationSiteIncome = reservationPrice * siteRevenue / 100;
		reservationIncome = reservationPrice - reservationSiteIncome
				+ ((reservationPrice - reservationSiteIncome) * ownerRevenue) / 100;
		reservationSiteIncome -= ((reservationPrice - reservationSiteIncome) * ownerRevenue) / 100;

		cottageReservation.setPrice(reservationPrice);
		cottageReservation.setOwnerIncome(reservationIncome);
		cottageReservation.setSiteIncome(reservationSiteIncome);

		return cottageReservation;
	}

	private boolean isCustomersReservationInAction(CottageReservation newReservation,
			CottageReservation existingReservation) {
		return (existingReservation.getCustomer().getId() == newReservation.getCustomer().getId())
				&& existingReservation.getDuration().isBetween(LocalDateTime.now());
	}

	private void reserveAvailableDateSpan(CottageReservation cottageReservation, DateTimeSpan availableDateSpan) {
		Cottage cottage = cottageReservation.getCottage();
		DateTimeSpan duration = cottageReservation.getDuration();
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CottageReservationDTO reserveCottageOwner(CottageReservationDTO cottageReservationDTO, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		CottageReservation cottageReservation = CottageReservationMapper
				.CottageReservationDTOToCottageReservation(cottageReservationDTO);

		try {
			Cottage cottage = cottageRepository.getNotLockedCottage(cottageReservation.getCottage().getId());
			Customer customer = customerRepository.findById(cottageReservationDTO.getCustomer().getId()).get();
			CottageOwner owner = cottageOwnerRepository.findById(cottage.getCottageOwner().getId()).get();
			cottageReservation.setConfirmed(false);
			cottageReservation.setCustomer(customer);
			if (cottageReservationDTO.getPrice() == 0) {
				cottageReservation = calculateIncome(cottageReservation);
			} else {
				cottageReservation = calculateIncomeWithoutCustomerDiscount(cottageReservation);
			}
			customerService.promoteLoyaltyCustomer(customer);
			promoteLoyaltyCottageOwner(owner);

			if (cottageReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
				return null;
			}

			boolean inAction = false;
			for (CottageReservation q : cottageReservationRepository
					.findByConfirmedIsTrueAndCottageId(cottageReservation.getCottage().getId())) {

				if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
					return null;
				}

				if (isCustomersReservationInAction(cottageReservation, q)) {
					inAction = true;
				}
			}

			if (!inAction) {
				return null;
			}

			for (DateTimeSpan dateTimeSpan : cottageReservation.getCottage().getUnavailableReservationDateSpan()) {
				if (dateTimeSpan.overlapsWith(cottageReservation.getDuration())) {
					return null;
				}
			}

			for (DateTimeSpan dateTimeSpan : cottageOwnerRepository
					.findById(cottageReservation.getCottage().getCottageOwner().getId()).get()
					.getUnavailableReservationDateSpan()) {
				if (dateTimeSpan.overlapsWith(cottageReservation.getDuration())) {
					return null;
				}
			}

			for (CottageQuickReservation q : cottageQuickReservationRepository
					.findByIsReservedFalseAndCottageId(cottageReservation.getCottage().getId())) {

				if (q.getDuration().overlapsWith(cottageReservation.getDuration())) {
					return null;
				}
			}

			for (DateTimeSpan dateTimeSpan : cottageReservation.getCottage().getAvailableReservationDateSpan()) {
				if (cottageReservation.getDuration().overlapsWith(dateTimeSpan)) {
					reserveAvailableDateSpan(cottageReservation, dateTimeSpan);
					break;
				}
			}
			CottageReservation cottageReservationReturn = cottageReservationRepository.save(cottageReservation);

			customerService.sendReservationConfirmationEmail(siteUrl, cottageReservationReturn);
			System.out.println(cottageReservationReturn.getDuration().getStartDate());
			return CottageReservationMapper.CottageReservationToCottageReservationDTO(cottageReservationReturn);
		} catch (PessimisticEntityLockException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private CottageReservation calculateIncomeWithoutCustomerDiscount(CottageReservation cottageReservation) {
		LoyaltySettingsDTO loyaltySettings = loyaltySettingsService.getLoyaltySettings();
		Double ownerRevenue = loyaltySettingsService
				.getOwnerRevenue(cottageReservation.getCottage().getCottageOwner().getLoyaltyProgram());
		Double siteRevenue = loyaltySettings.getSystemRevenue();
		Double cottagePrice = cottageReservation.getPrice();
		Double reservationSiteIncome = 0.0;
		Double reservationIncome = 0.0;

		reservationSiteIncome = cottagePrice * siteRevenue / 100;
		reservationIncome = cottagePrice - reservationSiteIncome
				+ ((cottagePrice - reservationSiteIncome) * ownerRevenue) / 100;
		reservationSiteIncome -= ((cottagePrice - reservationSiteIncome) * ownerRevenue) / 100;

		cottageReservation.setOwnerIncome(reservationIncome);
		cottageReservation.setSiteIncome(reservationSiteIncome);

		return cottageReservation;
	}

	@Transactional
	@Override
	public Set<CottageReservationDTO> findByCottageId(Long id) {
		Set<CottageReservation> cottageReservations = new HashSet<>(
				cottageReservationRepository.findByConfirmedIsTrueAndCottageId(id));
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
		cottageReservationRepository.deleteById(id);
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
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(),
							newAvailableDateSpan.getEndDate());
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
					newAvailableDateSpan = new DateTimeSpan(newAvailableDateSpan.getStartDate(),
							dateTimeSpan.getEndDate());
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CottageReservationDTO confirmReservation(Long id) {
		try {
			CottageReservation cottageReservation = cottageReservationRepository.getNotLockedCottageReservation(id);
			cottageReservation.setConfirmed(true);
			return CottageReservationMapper
					.CottageReservationToCottageReservationDTO(cottageReservationRepository.save(cottageReservation));

		} catch (PessimisticEntityLockException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public Set<CottageReservationDTO> findByCottageCottageOwnerId(Long id) {
		Set<CottageReservation> cottageReservations = new HashSet<>(
				cottageReservationRepository.findByConfirmedIsTrueAndCottage_CottageOwner_Id(id));
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

	@Override
	public Set<CottageReservationDTO> findAllActiveByCottageOwnerId(Long id) {
		Set<CottageReservationDTO> dtos = new HashSet<>();
		for (CottageReservationDTO cottageReservationDTO : findByCottageCottageOwnerId(id)) {
			if (!cottageReservationDTO.getDuration().passed())
				dtos.add(cottageReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<CottageReservationDTO> findAllPastByCottageOwnerId(Long id) {
		Set<CottageReservationDTO> dtos = new HashSet<>();
		for (CottageReservationDTO cottageReservationDTO : findByCottageCottageOwnerId(id)) {
			if (cottageReservationDTO.getDuration().passed())
				dtos.add(cottageReservationDTO);
		}
		return dtos;
	}

	@Override
	public CottageReservationDTO cancelCottageReservation(CottageReservationDTO cottageReservationDTO) {
		CottageReservation cottageReservation = CottageReservationMapper
				.CottageReservationDTOToCottageReservation(cottageReservationDTO);
		LocalDateTime currentTime = LocalDateTime.now();
		if (currentTime.plusDays(3).compareTo(cottageReservation.getDuration().getStartDate()) >= 0) {
			throw new InvalidParameterException("You can`t cancel because today is 3 days to reservation");
		}
		cottageReservation.setConfirmed(false);
		this.freeReservedSpan(cottageReservation);
		return CottageReservationMapper
				.CottageReservationToCottageReservationDTO(cottageReservationRepository.save(cottageReservation));

	}

}
