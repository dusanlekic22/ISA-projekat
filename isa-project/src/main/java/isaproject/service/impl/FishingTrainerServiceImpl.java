package isaproject.service.impl;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.mapper.SortTypeMapper;
import isaproject.mapper.UserMapper;
import isaproject.mapper.boat.BoatMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingTrainer;
import isaproject.model.SortType;
import isaproject.repository.AddressRepository;
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
	private AddressRepository addressRepository;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;
	private FishingReservationService fishingReservationService;
	private FishingQuickReservationService fishingQuickReservationService;

	@Autowired
	public FishingTrainerServiceImpl(FishingTrainerRepository fishingTrainerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, AddressRepository addressRepository,
			BusinessOwnerRegistrationRequestRepository registrationRequestRepository,
			FishingReservationService fishingReservationService,
			FishingQuickReservationService fishingQuickReservationService) {
		super();
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.registrationRequestRepository = registrationRequestRepository;
		this.fishingReservationService = fishingReservationService;
		this.fishingQuickReservationService = fishingQuickReservationService;
	}
	
	
	public Page<FishingTrainerDTO> findAllPagination(List<SortTypeDTO> sortTypesDTO, Pageable pageable) {
		List<Sort.Order> sorts = new ArrayList<>();
		List<SortType> sortTypes = sortTypesDTO.stream().map(sortTypeDTO -> SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO)).collect(Collectors.toList());
		if(sortTypes !=null) {
			for (SortType sortType : sortTypes) {
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}

		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
		
		return UserMapper.pageFishingTrainerToPageDTO(fishingTrainerRepository.findAll(paging));
		}else {
			return UserMapper.pageFishingTrainerToPageDTO(fishingTrainerRepository.findAll(pageable));
		}
		}

	@Override
	public FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO) {
		addressRepository.save(businessOwnerDTO.getAddress());
		FishingTrainer fishingTrainer = UserMapper.DTOToFishingTrainer(businessOwnerDTO);
		fishingTrainer.setRoles(roleService.findByName("ROLE_FISHING_TRAINER"));
		fishingTrainer.setPassword(passwordEncoder.encode(businessOwnerDTO.getPassword()));
		fishingTrainer.setEnabled(false);

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

}
