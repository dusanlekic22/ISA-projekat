package isaproject.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.dto.FishingTrainerAvailabilityDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.mapper.GradeMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingTrainer;
import isaproject.model.Grade;
import isaproject.model.LoyaltyProgram;
import isaproject.model.LoyaltyRank;
import isaproject.model.SortType;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.service.FishingQuickReservationService;
import isaproject.service.FishingReservationService;
import isaproject.service.FishingTrainerService;
import isaproject.service.RoleService;

@Service
public class FishingTrainerServiceImpl implements FishingTrainerService {

	private FishingTrainerRepository fishingTrainerRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;
	private FishingReservationService fishingReservationService;
	private FishingQuickReservationService fishingQuickReservationService;

	@Autowired
	public FishingTrainerServiceImpl(FishingTrainerRepository fishingTrainerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, BusinessOwnerRegistrationRequestRepository registrationRequestRepository,
			FishingReservationService fishingReservationService,
			FishingQuickReservationService fishingQuickReservationService) {
		super();
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.registrationRequestRepository = registrationRequestRepository;
		this.fishingReservationService = fishingReservationService;
		this.fishingQuickReservationService = fishingQuickReservationService;
	}

	public Set<FishingTrainerDTO> getAll() {
		return fishingTrainerRepository.findAll().stream()
				.map((fishingTrainer -> UserMapper.FishingTrainerToDTO(fishingTrainer))).collect(Collectors.toSet());
	}

	public Page<FishingTrainerDTO> findAllPagination(List<SortTypeDTO> sortTypesDTO, Pageable pageable) {
		List<Sort.Order> sorts = new ArrayList<>();
		List<SortType> sortTypes = sortTypesDTO.stream()
				.map(sortTypeDTO -> SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO)).collect(Collectors.toList());
		if (sortTypes != null) {
			for (SortType sortType : sortTypes) {
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}

			Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));

			return UserMapper.pageFishingTrainerToPageDTO(fishingTrainerRepository.findAll(paging));
		} else {
			return UserMapper.pageFishingTrainerToPageDTO(fishingTrainerRepository.findAll(pageable));
		}
	}

	@Override
	public FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO) {
		FishingTrainer fishingTrainer = UserMapper.DTOToFishingTrainer(businessOwnerDTO);
		fishingTrainer.setRoles(roleService.findByName("ROLE_FISHING_TRAINER"));
		fishingTrainer.setPassword(passwordEncoder.encode(businessOwnerDTO.getPassword()));
		fishingTrainer.setEnabled(false);
		LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
		loyaltyProgram.setLoyaltyRank(LoyaltyRank.Regular);
		loyaltyProgram.setPoints(0);
		fishingTrainer.setLoyaltyProgram(loyaltyProgram);

		BusinessOwnerRegistrationRequest request = new BusinessOwnerRegistrationRequest();
		request.setAccepted(null);
		request.setRegistrationExplanation(businessOwnerDTO.getRegistrationExplanation());
		request.setUserEmail(fishingTrainer.getEmail());
		registrationRequestRepository.save(request);

		return this.fishingTrainerRepository.save(fishingTrainer);
	}

	@Override
	public FishingTrainerDTO findByUsername(String username) {
		FishingTrainer fishingTrainer = fishingTrainerRepository.findByUsername(username);
		return UserMapper.FishingTrainerToDTO(fishingTrainer);
	}

	@Transactional
	@Override
	public FishingTrainerDTO updateAvailableTerms(Long id, DateTimeSpan dateTimeSpan) {
		FishingTrainer fishingTrainer = fishingTrainerRepository.findById(id).get();

		for (FishingReservationDTO fishingReservation : fishingReservationService
				.findByFishingCourseFishingTrainerId(id)) {
			if (dateTimeSpan.overlapsWith(fishingReservation.getDuration()))
				return null;
		}
		for (FishingQuickReservationDTO fishingQuickReservation : fishingQuickReservationService
				.findByFishingCourseFishingTrainerId(id)) {
			if (dateTimeSpan.overlapsWith(fishingQuickReservation.getDuration()))
				return null;
		}

		boolean overlapped = false;
		Set<DateTimeSpan> availaDateSpans = new HashSet<>(fishingTrainer.getAvailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : availaDateSpans) {
			if (reservationSpan.overlapsWith(dateTimeSpan)) {
				fishingTrainer.getAvailableReservationDateSpan().remove(reservationSpan);
				if (dateTimeSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (dateTimeSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), dateTimeSpan.getEndDate());
				}
				overlapped = true;
				fishingTrainer.getAvailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			fishingTrainer.getAvailableReservationDateSpan().add(dateTimeSpan);

		for (DateTimeSpan unavailableDateSpan : fishingTrainer.getUnavailableReservationDateSpan()) {
			if (dateTimeSpan.overlapsWith(unavailableDateSpan)) {
				reserveUnavailableDateSpan(fishingTrainer, dateTimeSpan, unavailableDateSpan);
				break;
			}
		}

		return UserMapper.FishingTrainerToDTO(fishingTrainerRepository.save(fishingTrainer));
	}

	private void reserveUnavailableDateSpan(FishingTrainer fishingTrainer, DateTimeSpan availableDateSpan,
			DateTimeSpan unavailableDateSpan) {
		fishingTrainer.getUnavailableReservationDateSpan().remove(unavailableDateSpan);
		if (availableDateSpan.getStartDate().compareTo(unavailableDateSpan.getStartDate()) <= 0
				&& availableDateSpan.getEndDate().compareTo(unavailableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getEndDate(),
					unavailableDateSpan.getEndDate());
			fishingTrainer.getUnavailableReservationDateSpan().add(newDateSpan);
		} else if (availableDateSpan.getStartDate().compareTo(unavailableDateSpan.getStartDate()) >= 0
				&& availableDateSpan.getEndDate().compareTo(unavailableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(unavailableDateSpan.getStartDate(),
					availableDateSpan.getStartDate());

			fishingTrainer.getUnavailableReservationDateSpan().add(newDateSpan);
		} else if (availableDateSpan.getStartDate().compareTo(unavailableDateSpan.getStartDate()) >= 0
				&& availableDateSpan.getEndDate().compareTo(unavailableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(unavailableDateSpan.getStartDate(),
					availableDateSpan.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(availableDateSpan.getEndDate(),
					unavailableDateSpan.getEndDate());

			fishingTrainer.getUnavailableReservationDateSpan().add(newDateSpan1);
			fishingTrainer.getUnavailableReservationDateSpan().add(newDateSpan2);
		}
		fishingTrainerRepository.save(fishingTrainer);
	}

	@Transactional
	@Override
	public FishingTrainerDTO updateUnavailableTerms(Long id, DateTimeSpan newDateSpan) {
		FishingTrainer fishingTrainer = fishingTrainerRepository.findById(id).get();
		for (FishingReservationDTO fishingTrainerReservation : fishingReservationService
				.findByFishingCourseFishingTrainerId(id)) {
			if (newDateSpan.overlapsWith(fishingTrainerReservation.getDuration()))
				return null;
		}
		for (FishingQuickReservationDTO fishingTrainerQuickReservation : fishingQuickReservationService
				.findByFishingCourseFishingTrainerId(id)) {
			if (newDateSpan.overlapsWith(fishingTrainerQuickReservation.getDuration()))
				return null;
		}
		boolean overlapped = false;
		Set<DateTimeSpan> unavailableDateSpans = new HashSet<>(fishingTrainer.getUnavailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : unavailableDateSpans) {
			if (reservationSpan.overlapsWith(newDateSpan)) {
				fishingTrainer.getUnavailableReservationDateSpan().remove(reservationSpan);
				if (newDateSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(newDateSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (newDateSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), newDateSpan.getEndDate());
				}
				overlapped = true;
				fishingTrainer.getUnavailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			fishingTrainer.getUnavailableReservationDateSpan().add(newDateSpan);

		for (DateTimeSpan dateTimeSpan : fishingTrainer.getAvailableReservationDateSpan()) {
			if (newDateSpan.overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpan(fishingTrainer, newDateSpan, dateTimeSpan);
				break;
			}
		}

		return UserMapper.FishingTrainerToDTO(fishingTrainerRepository.save(fishingTrainer));
	}

	private void reserveAvailableDateSpan(FishingTrainer fishingTrainer, DateTimeSpan unavailableDateSpan,
			DateTimeSpan availableDateSpan) {
		fishingTrainer.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (unavailableDateSpan.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& unavailableDateSpan.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(unavailableDateSpan.getEndDate(),
					availableDateSpan.getEndDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (unavailableDateSpan.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& unavailableDateSpan.getEndDate().compareTo(availableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(),
					unavailableDateSpan.getStartDate());

			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (unavailableDateSpan.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& unavailableDateSpan.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(),
					unavailableDateSpan.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(unavailableDateSpan.getEndDate(),
					availableDateSpan.getEndDate());

			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan1);
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		fishingTrainerRepository.save(fishingTrainer);
	}

	@Override
	public Page<FishingTrainerDTO> findByAvailability(FishingTrainerAvailabilityDTO fishingTrainerAvailability,
			Pageable pageable) {

		String name = "%";
		Double grade = -1.0;

		if (fishingTrainerAvailability.getName() != null) {
			name = name + fishingTrainerAvailability.getName().toLowerCase().concat("%");
		}
		if (fishingTrainerAvailability.getGrade() != null) {
			grade = fishingTrainerAvailability.getGrade();
		}
		Boolean isLocationSortDisabled = true;

		List<Sort.Order> sorts = new ArrayList<>();
		if (fishingTrainerAvailability.getSortBy() != null && fishingTrainerAvailability.getSortBy().size() != 0) {

			FishingTrainerDTO dto;
			for (SortType sortType : fishingTrainerAvailability.getSortBy()) {
				if (sortType != null && sortType.getField().equals("latitude")) {
					isLocationSortDisabled = false;
					sortType.setField("a." + sortType.getField());
					if (sortType.getDirection().toLowerCase().contains("desc")) {
						sorts.add(new Sort.Order(Sort.Direction.DESC, "a.longitude"));
					} else {
						sorts.add(new Sort.Order(Sort.Direction.ASC, "a.longitude"));
					}
				}
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}
		}

		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
		List<FishingTrainer> availableFishingTrainers;
		List<FishingTrainerDTO> availableFishingTrainersWithPrice;

		if (fishingTrainerAvailability.getDateSpan() == null
				|| fishingTrainerAvailability.getDateSpan().getStartDate() == null
				|| fishingTrainerAvailability.getDateSpan().getEndDate() == null) {
			return searchFishingTrainer(name, grade, isLocationSortDisabled, paging);
		} else {
			return checkAvailabilty(fishingTrainerAvailability, name, grade, isLocationSortDisabled, paging);
		}

	}

	private Page<FishingTrainerDTO> checkAvailabilty(FishingTrainerAvailabilityDTO fishingTrainerAvailability,
			String name, Double grade, Boolean isLocationSortDisabled, Pageable paging) {
		List<FishingTrainer> availableFishingTrainers;
		LocalDateTime start = fishingTrainerAvailability.getDateSpan().getStartDate();
		LocalDateTime end = fishingTrainerAvailability.getDateSpan().getEndDate();
		Page<FishingTrainer> pageFishingTrainer;
		if (isLocationSortDisabled) {
			pageFishingTrainer = fishingTrainerRepository.getAvailability(start, end, name, grade, paging);
		} else {
			pageFishingTrainer = fishingTrainerRepository.getAvailabilityWithSortLocation(start, end, name, grade,
					paging);
		}
		availableFishingTrainers = pageFishingTrainer.getContent();

		Page<FishingTrainerDTO> pc = new PageImpl(availableFishingTrainers, paging,
				pageFishingTrainer.getTotalElements());
		return pc;
	}

	private Page<FishingTrainerDTO> searchFishingTrainer(String name, Double grade, Boolean isLocationSortDisabled,
			Pageable paging) {
		List<FishingTrainer> availableFishingTrainers;
		Page<FishingTrainer> pageFishingTrainer;
		if (isLocationSortDisabled) {
			pageFishingTrainer = fishingTrainerRepository.searchFishingTrainer(name, grade, paging);
		} else {
			pageFishingTrainer = fishingTrainerRepository.searchFishingTrainerWithSortLocation(name, grade, paging);
		}
		availableFishingTrainers = pageFishingTrainer.getContent();
		return new PageImpl(availableFishingTrainers, paging, pageFishingTrainer.getTotalElements());
	}

	@Override
	@Transactional
	public FishingTrainerDTO addGrade(GradeDTO gradeDTO, long fishingId) {
		gradeDTO.setIsAccepted(null);
		FishingTrainer fishingTrainer = fishingTrainerRepository.findById(fishingId).get();
		for (Grade grade : fishingTrainer.getGrades()) {
			if (grade.getUser().getId() == gradeDTO.getUser().getId()) {
				fishingTrainer.getGrades().remove(grade);
				break;
			}
		}
		fishingTrainer.getGrades().add(GradeMapper.GradeDTOToGrade(gradeDTO));
		fishingTrainerRepository.save(fishingTrainer);
		return UserMapper.FishingTrainerToDTO(fishingTrainer);
	}

	@Override
	public FishingTrainerDTO findById(Long id) {
		return UserMapper.FishingTrainerToDTO(fishingTrainerRepository.findById(id).get());
	}

	@Override
	@Transactional
	public FishingTrainerDTO updateBio(Long id, String bio) {
		FishingTrainer fishingTrainer = fishingTrainerRepository.findById(id).get();
		fishingTrainer.setBiography(bio);
		return UserMapper.FishingTrainerToDTO(fishingTrainerRepository.save(fishingTrainer));
	}

}
