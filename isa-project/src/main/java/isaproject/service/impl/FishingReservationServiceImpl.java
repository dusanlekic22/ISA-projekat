package isaproject.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CustomerDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.mapper.CustomerMapper;
import isaproject.mapper.FishingReservationMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.mapper.boat.BoatReservationMapper;
import isaproject.dto.LoyaltySettingsDTO;
import isaproject.mapper.CustomerMapper;
import isaproject.mapper.FishingReservationMapper;
import isaproject.model.AdditionalService;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingCourse;
import isaproject.model.FishingQuickReservation;
import isaproject.model.FishingReservation;
import isaproject.model.FishingTrainer;
import isaproject.model.SortType;
import isaproject.repository.CustomerRepository;
import isaproject.repository.FishingQuickReservationRepository;
import isaproject.repository.FishingReservationRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.service.CustomerService;
import isaproject.service.FishingReservationService;
import isaproject.service.FishingTrainerService;
import isaproject.service.LoyaltySettingsService;

@Service
public class FishingReservationServiceImpl implements FishingReservationService {

	FishingReservationRepository fishingeReservationRepository;
	FishingQuickReservationRepository fishingeQuickReservationRepository;
	CustomerService customerService;
	CustomerRepository customerRepository;
	FishingTrainerRepository fishingTrainerRepository;
	FishingTrainerService fishingTrainerService;
	LoyaltySettingsService loyaltySettingsService;

	@Autowired
	public FishingReservationServiceImpl(FishingReservationRepository fishingeReservationRepository,
			FishingQuickReservationRepository fishingeQuickReservationRepository, CustomerService customerService,
			CustomerRepository customerRepository, FishingTrainerRepository fishingTrainerRepository,
			FishingTrainerService fishingTrainerService, LoyaltySettingsService loyaltySettingsService) {
		super();
		this.fishingeReservationRepository = fishingeReservationRepository;
		this.fishingeQuickReservationRepository = fishingeQuickReservationRepository;
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.fishingTrainerService = fishingTrainerService;
		this.loyaltySettingsService = loyaltySettingsService;
	}

	@Override
	public FishingReservationDTO findById(Long id) {
		FishingReservation fishingeReservation = fishingeReservationRepository.findById(id).orElse(null);
		return FishingReservationMapper.FishingReservationToDTO(fishingeReservation);
	}

	@Override
	public Set<FishingReservationDTO> findAll() {
		Set<FishingReservation> fishinges = new HashSet<>(fishingeReservationRepository.findAll());
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservation fr : fishinges) {
			dtos.add(FishingReservationMapper.FishingReservationToDTO(fr));
		}
		return dtos;
	}
	
	@Override
	public Page<FishingReservationDTO> findAllPagination(Long id, SortTypeDTO sortTypeDTO, Pageable pageable) {


	    SortType sortType =  SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO);
	    Sort sort = Sort.by("id").ascending()  ;
		if (sortType != null && !sortType.getDirection().equalsIgnoreCase("")) {
				if (sortType.getDirection() !=null && sortType.getDirection().toLowerCase().contains("desc")) {
					sort = Sort.by(sortType.getField()).descending();
				} else if ( sortType.getDirection() !=null && sortType.getDirection().toLowerCase().contains("asc")) {
					sort = Sort.by(sortType.getField()).ascending();
				}
			
				
			Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

			return FishingReservationMapper.pageFishingReservationToPageFishingReservationDTO(
					fishingeReservationRepository.findCustomerReservationsSortByDuration(id, paging));
		} else {
			return FishingReservationMapper.pageFishingReservationToPageFishingReservationDTO(fishingeReservationRepository.findCustomerReservationsSortByDuration(id, pageable));
		}
	}
	
	@Override
	public Page<FishingReservationDTO> findAllIncomingPagination(Long id, SortTypeDTO sortTypeDTO, Pageable pageable) {

		  SortType sortType =  SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO);
		    Sort sort = Sort.by("id").ascending()  ;
			if (sortType != null && !sortType.getDirection().equalsIgnoreCase("")) {
					if (sortType.getDirection() !=null && sortType.getDirection().toLowerCase().contains("desc")) {
						sort = Sort.by(sortType.getField()).descending();
					} else if ( sortType.getDirection() !=null && sortType.getDirection().toLowerCase().contains("asc")) {
						sort = Sort.by(sortType.getField()).ascending();
					}
			Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sort);

			return FishingReservationMapper.pageFishingReservationToPageFishingReservationDTO(
					fishingeReservationRepository.findIncomingCustomerReservationsSortByDuration(id, paging));
		} else {
			return FishingReservationMapper.pageFishingReservationToPageFishingReservationDTO(fishingeReservationRepository.findIncomingCustomerReservationsSortByDuration(id, pageable));
		}
	}
	

	@Override
	public Set<CustomerDTO> findCustomersHasCurrentReservation(long fishingCourseId) {
		Set<Customer> customers = new HashSet<>(customerRepository.findAll());
		Set<CustomerDTO> dtos = new HashSet<>();
		if (customers.size() != 0) {
			CustomerDTO dto;
			for (Customer p : customers) {
				for (FishingReservation fishingeReservation : p.getFishingReservation()) {
					if (fishingeReservation.getDuration().isBetween(LocalDateTime.now()) && fishingeReservation.getFishingCourse().getId() == fishingCourseId) {
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
	public FishingReservationDTO reserveCustomer(FishingReservationDTO fishingReservationDTO) {
		FishingReservation fishingReservation = FishingReservationMapper
				.DTOToFishingReservation(fishingReservationDTO);
		Customer customer = customerRepository.findById(fishingReservationDTO.getCustomer().getId()).get();
		FishingTrainer owner = fishingTrainerRepository.findById(fishingReservationDTO.getFishingCourse().getFishingTrainer().getId()).get();
		fishingReservation.setConfirmed(true);
		fishingReservation.setCustomer(customer);
		fishingReservation = calculateIncome(fishingReservation);
		customerService.promoteLoyaltyCustomer(customer);
		fishingTrainerService.promoteLoyaltyFishingTrainer(owner);
		
		if (fishingReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}

		for (FishingReservation q : fishingeReservationRepository
				.findByConfirmedIsTrueAndFishingCourse_FishingTrainer_Id(fishingReservation.getFishingCourse().getFishingTrainer().getId())) {
			if (q.getDuration().overlapsWith(fishingReservation.getDuration())) {
				return null;
			}
		}

		boolean overlaps = false;
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingReservation.getFishingCourse().getFishingTrainer().getUsername());
		for (DateTimeSpan dateTimeSpan : fishingTrainer.getAvailableReservationDateSpan()) {
			if (fishingReservation.getDuration().overlapsWith(dateTimeSpan)) {
				overlaps = true;
				reserveAvailableDateSpanForCustomer(fishingReservation, dateTimeSpan);
				break;
			}
		}

		if (!overlaps) {
			return null;}

		return FishingReservationMapper
				.FishingReservationToDTO(fishingeReservationRepository.save(fishingReservation));
	}
	
	private FishingReservation calculateIncome(FishingReservation fishingReservation) {
		LoyaltySettingsDTO loyaltySettings = loyaltySettingsService.getLoyaltySettings();
		Double cutomerDiscount = loyaltySettingsService.getCustomerDiscount(fishingReservation.getCustomer().getLoyaltyProgram());
		Double ownerRevenue = loyaltySettingsService.getOwnerRevenue(fishingReservation.getFishingCourse().getFishingTrainer().getLoyaltyProgram());
		Double siteRevenue = loyaltySettings.getSystemRevenue();
		Double fishingPrice = fishingReservation.getFishingCourse().getPrice() * fishingReservation.getDuration().getHours();
		Double reservationPrice = 0.0;
		Double reservationSiteIncome = 0.0;
		Double reservationIncome = 0.0;
		
		if (fishingReservation.getAdditionalService() != null) {
			for (AdditionalService additionalService : fishingReservation.getAdditionalService()) {
				fishingPrice += additionalService.getPrice();
			}
		}
		reservationPrice = fishingPrice - (fishingPrice * cutomerDiscount) / 100;
		reservationSiteIncome = reservationPrice * siteRevenue / 100;
		reservationIncome = reservationPrice - reservationSiteIncome + ((reservationPrice - reservationSiteIncome) * ownerRevenue) / 100;
		reservationSiteIncome -= ((reservationPrice - reservationSiteIncome) * ownerRevenue) / 100;
		
		fishingReservation.setPrice(reservationPrice);
		fishingReservation.setOwnerIncome(reservationIncome);
		fishingReservation.setSiteIncome(reservationSiteIncome);
		
		return fishingReservation;
	}

	private boolean isCustomersReservationInAction(FishingReservation newReservation,
			FishingReservation existingReservation) {
		return (existingReservation.getCustomer().getId() == newReservation.getCustomer().getId())
				&& existingReservation.getDuration().isBetween(LocalDateTime.now());
	}

	private void reserveAvailableDateSpanForOwner(FishingReservation fishingeReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingeCourse = fishingeReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingeReservation.getDuration();
		fishingTrainer.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());

			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan1);
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		fishingTrainerRepository.save(fishingTrainer);
	}

	private void reserveAvailableDateSpanForCustomer(FishingReservation fishingeReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingeCourse = fishingeReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingeReservation.getDuration();
		fishingTrainer.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) == 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) == 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());

			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());

			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan1);
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		fishingTrainerRepository.save(fishingTrainer);
	}

	@Transactional
	@Override
	public FishingReservationDTO reserveFishingOwner(FishingReservationDTO fishingeReservationDTO, String siteUrl) {
		FishingReservation fishingeReservation = FishingReservationMapper
				.DTOToFishingReservation(fishingeReservationDTO);
		fishingeReservation.setConfirmed(false);
		fishingeReservation
				.setCustomer(customerRepository.findById(fishingeReservationDTO.getCustomer().getId()).get());
		if (fishingeReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}

		boolean inAction = false;
		for (FishingReservation q : fishingeReservationRepository
				.findByConfirmedIsTrueAndFishingCourse_FishingTrainer_Id(fishingeReservation.getFishingCourse().getFishingTrainer().getId())) {

			if (q.getDuration().overlapsWith(fishingeReservation.getDuration())) {
				return null;
			}

			if (isCustomersReservationInAction(fishingeReservation, q)) {
				inAction = true;
			}
		}

		for (FishingQuickReservation q : fishingeQuickReservationRepository
				.findByFishingCourse_FishingTrainer_Id(fishingeReservation.getFishingCourse().getFishingTrainer().getId())) {

			if (q.getDuration().overlapsWith(fishingeReservation.getDuration())) {
				return null;
			}
		}

		if (!inAction) {
			return null;
		}

		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeReservation.getFishingCourse().getFishingTrainer().getUsername());
		for (DateTimeSpan dateTimeSpan : fishingTrainer.getAvailableReservationDateSpan()) {
			if (fishingeReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpanForOwner(fishingeReservation, dateTimeSpan);
				break;
			}
		}
		FishingReservation fishingeReservationReturn = fishingeReservationRepository.save(fishingeReservation);

		customerService.sendReservationConfirmationEmail(siteUrl, fishingeReservationReturn);

		return FishingReservationMapper.FishingReservationToDTO(fishingeReservationReturn);
	}

	private FishingReservation calculateIncomeWithoutCustomerDiscount(FishingReservation fishingReservation) {
		LoyaltySettingsDTO loyaltySettings = loyaltySettingsService.getLoyaltySettings();
		Double ownerRevenue = loyaltySettingsService.getOwnerRevenue(fishingReservation.getFishingCourse().getFishingTrainer().getLoyaltyProgram());
		Double siteRevenue = loyaltySettings.getSystemRevenue();
		Double fishingPrice = fishingReservation.getPrice();
		Double reservationSiteIncome = 0.0;
		Double reservationIncome = 0.0;
		
		reservationSiteIncome = fishingPrice * siteRevenue / 100;
		reservationIncome = fishingPrice - reservationSiteIncome + ((fishingPrice - reservationSiteIncome) * ownerRevenue) / 100;
		reservationSiteIncome -= ((fishingPrice - reservationSiteIncome) * ownerRevenue) / 100;
		
		fishingReservation.setOwnerIncome(reservationIncome);
		fishingReservation.setSiteIncome(reservationSiteIncome);
		
		return fishingReservation;
	}
	
	@Transactional
	@Override
	public Set<FishingReservationDTO> findByFishingCourseFishingTrainerId(Long id) {
		Set<FishingReservation> fishingeReservations = new HashSet<>(
				fishingeReservationRepository.findByConfirmedIsTrueAndFishingCourse_FishingTrainer_Id(id));
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservation p : fishingeReservations) {
			dtos.add(FishingReservationMapper.FishingReservationToDTO(p));
		}
		return dtos;
	}

	@Transactional
	@Override
	public FishingReservationDTO deleteById(Long id) {
		FishingReservation fishingeReservation = fishingeReservationRepository.findById(id).get();
		freeReservedSpan(fishingeReservation);
		fishingeQuickReservationRepository.deleteById(id);
		return FishingReservationMapper.FishingReservationToDTO(fishingeReservation);
	}

	private void freeReservedSpan(FishingReservation fishingeReservation) {
		FishingCourse fishingeCourse = fishingeReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingeReservation.getDuration();
		DateTimeSpan newAvailableDateSpan = new DateTimeSpan(duration);
		Set<DateTimeSpan> availableDateSpans = new HashSet<>(fishingTrainer.getAvailableReservationDateSpan());
		boolean startChanged = false;
		boolean endChanged = false;
		for (DateTimeSpan dateTimeSpan : availableDateSpans) {
			if (newAvailableDateSpan.getStartDate().compareTo(dateTimeSpan.getEndDate()) == 0) {
				fishingTrainer.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (endChanged) {
					fishingTrainer.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(),
							newAvailableDateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), duration.getEndDate());
					startChanged = true;
				}
				fishingTrainer.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
			if (newAvailableDateSpan.getEndDate().compareTo(dateTimeSpan.getStartDate()) == 0) {
				fishingTrainer.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (startChanged) {
					fishingTrainer.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(newAvailableDateSpan.getStartDate(),
							dateTimeSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(duration.getStartDate(), dateTimeSpan.getEndDate());
					endChanged = true;
				}
				fishingTrainer.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
		}

		fishingTrainerRepository.save(fishingTrainer);
	}

	@Override
	public Set<FishingReservationDTO> findAllPast() {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findAll()) {
			if (fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllActive() {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findAll()) {
			if (!fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllPastByFishingCourseId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseId(id)) {
			if (fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllActiveByFishingCourseId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseId(id)) {
			if (!fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Transactional
	@Override
	public Set<FishingReservationDTO> findByFishingCourseId(Long id) {
		Set<FishingReservation> fishingeReservations = new HashSet<>(
				fishingeReservationRepository.findByConfirmedIsTrueAndFishingCourseId(id));
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservation p : fishingeReservations) {
			dtos.add(FishingReservationMapper.FishingReservationToDTO(p));
		}
		return dtos;
	}

	@Override
	public FishingReservationDTO confirmReservation(Long id) {
		FishingReservation fishingeReservation = fishingeReservationRepository.findById(id).get();
		fishingeReservation.setConfirmed(true);
		return FishingReservationMapper
				.FishingReservationToDTO(fishingeReservationRepository.save(fishingeReservation));
	}

	@Override
	public Set<FishingReservationDTO> findAllActiveByFishingTrainerId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseFishingTrainerId(id)) {
			if (!fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllPastByFishingTrainerId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseFishingTrainerId(id)) {
			if (fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

}
